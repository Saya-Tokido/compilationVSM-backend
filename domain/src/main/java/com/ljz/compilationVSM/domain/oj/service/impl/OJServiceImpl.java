package com.ljz.compilationVSM.domain.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljz.compilationVSM.infrastructure.mapper.*;
import com.ljz.compilationVSM.infrastructure.queryDTO.LexerPDCodeQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljz.compilationVSM.common.constant.Constants;
import com.ljz.compilationVSM.common.dto.LexerTestCaseDTO;
import com.ljz.compilationVSM.common.enums.CompileStatusEnum;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.common.utils.*;
import com.ljz.compilationVSM.dependency.dto.CompilationInputDTO;
import com.ljz.compilationVSM.dependency.dto.CompilationOutputDTO;
import com.ljz.compilationVSM.dependency.facade.RemoteCompilerFacade;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.StudentBaseInfoResponseDTO;
import com.ljz.compilationVSM.domain.convert.OJDTOMapping;
import com.ljz.compilationVSM.domain.oj.dto.*;
import com.ljz.compilationVSM.domain.oj.service.OJService;
import com.ljz.compilationVSM.infrastructure.po.*;
import com.ljz.compilationVSM.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * OJ服务类
 *
 * @author ljz
 * @since 2024-12-01
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OJServiceImpl implements OJService {

    private final MethodNameRepository methodNameRepository;
    private final MethodBodyRepository methodBodyRepository;
    private final MethodTestcaseRepository methodTestcaseRepository;
    private final LexerRepository lexerRepository;
    private final LexerTestcaseRepository lexerTestcaseRepository;
    private final LexerAnswerRepository lexerAnswerRepository;
    private final LexerCodeRepository lexerCodeRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final LexerPDRepository lexerPDRepository;
    private final StudentMapper studentMapper;
    private final LexerAnswerMapper lexerAnswerMapper;
    private final ConfigMapper configMapper;
    private final LexerCodeMapper lexerCodeMapper;
    private final LexerMapper lexerMapper;
    private final OJDTOMapping ojConvert;
    private final RemoteCompilerFacade remoteCompilerFacade;
    private final RedisUtil redisUtil;
    private final SnowflakeIdGenerator idGenerator;
    private final SourceCodeUtil sourceCodeUtil;
    private final DistributeLockUtil lock;
    private final ThreadPoolUtil threadPoolUtil;

    private final String METHOD_LANGUAGE = "C++";

    /**
     * 词法分析器用例redis前缀
     */
    @Value("${redis-key-prefix.lexer-testcase}")
    private String lexerTestcasePrefix;

    /**
     * 词法分析器单个测试用例分值
     */
    @Value("${oj.lexer.testcase-weight}")
    private Integer LexerTestcaseWeight;

    /**
     * 词法分析器单次映射构建数量
     */
    @Value("${oj.lexer.pd.number}")
    private Integer lexerPDNumber;

    /**
     * 教学班列表分隔符
     */
    @Value("${info.teach-class.delimiter}")
    private String teachClassDelimiter;

    @Value("${distribute.lock.key-prefix.lexer-code-pd}")
    private String lexerCodePDLockKeyPrefix;

    @Value("${distribute.lock.timeout.lexer-code-pd}")
    private Long lexerCodePDLockTimeout;

    @Override
    public List<MethodResponseDTO> getMethodList(MethodListRequestDTO requestDTO) {
        LambdaQueryWrapper<MethodNamePO> queryWrapper = Wrappers.<MethodNamePO>lambdaQuery()
                .select(MethodNamePO::getId, MethodNamePO::getName, MethodNamePO::getLevel, MethodNamePO::getCommitNum, MethodNamePO::getPassNum)
                .eq(MethodNamePO::getIsDelete, Boolean.FALSE)
                .eq(MethodNamePO::getLanguage, requestDTO.getLanguage())
                .eq(MethodNamePO::getCompLanguage, requestDTO.getCompLanguage());
        return ojConvert.methodListConvert(methodNameRepository.list(queryWrapper));
    }

    @Override
    public MethodBodyResponseDTO getMethodBody(Long methodId) {
        LambdaQueryWrapper<MethodBodyPO> queryWrapper = Wrappers.<MethodBodyPO>lambdaQuery()
                .select(MethodBodyPO::getId, MethodBodyPO::getDescription, MethodBodyPO::getInput, MethodBodyPO::getOutput, MethodBodyPO::getInParam, MethodBodyPO::getOutParam, MethodBodyPO::getGlobalVar, MethodBodyPO::getChangedGlobal, MethodBodyPO::getPreMethod, MethodBodyPO::getBody)
                .eq(MethodBodyPO::getIsDelete, Boolean.FALSE)
                .eq(MethodBodyPO::getMethodId, methodId);
        return ojConvert.methodBodyConvert(methodBodyRepository.getOne(queryWrapper));
    }

    @Override
    public CodeReviewResponseDTO checkMethodCode(Long methodId, String code) {
        //todo 从redis获取用例
        LambdaQueryWrapper<MethodTestcasePO> queryWrapper = Wrappers.<MethodTestcasePO>lambdaQuery()
                .select(MethodTestcasePO::getPreCode, MethodTestcasePO::getTerminalInput, MethodTestcasePO::getTerminalOutput)
                .eq(MethodTestcasePO::getIsDelete, Boolean.FALSE)
                .eq(MethodTestcasePO::getMethodId, methodId);
        List<MethodTestcasePO> list = methodTestcaseRepository.list(queryWrapper);
        Iterator<MethodTestcasePO> iterator = list.iterator();
        // todo 后期优化为redis list获取
        while (iterator.hasNext()) {
            // 获取一个用例
            MethodTestcasePO next = iterator.next();
            // 拼接
            String compileCode = next.getPreCode() + "\n" + code;
            log.info("校验函数代码,拼接后的代码\n {}", compileCode);
            CodeReviewResponseDTO compileError = compile2("函数题编译", compileCode, next.getTerminalInput(), next.getTerminalOutput(), METHOD_LANGUAGE);
            if (Objects.nonNull(compileError)) return compileError;
        }
        return new CodeReviewResponseDTO(CompileStatusEnum.SUCCESS.getCode(), "通过全部用例");
    }

    @Override
    public LexerProblemResponseDTO getDemoProblem(String language, String compLanguage) {
        // 提交截止则直接返回
        ConfigPO config = configMapper.getConfig();
        if (config.getLexerDeadline().isBefore(LocalDateTime.now())) {
            log.info("查询词法分析器题目,考试已截止");
            throw new BizException(BizExceptionCodeEnum.LEXER_EXAM_CLOSED_ERROR);
        }
        // 查询示例代码
        LambdaQueryWrapper<LexerPO> queryWrapper1 = Wrappers.<LexerPO>lambdaQuery()
                .select(LexerPO::getId, LexerPO::getDescription)
                .eq(LexerPO::getIsDelete, Boolean.FALSE)
                .eq(LexerPO::getLanguage, language)
                .eq(LexerPO::getCompLanguage, compLanguage);
        LexerPO lexerPO = lexerRepository.getOne(queryWrapper1);
        if (Objects.isNull(lexerPO)) {
            log.warn("词法分析器题目获取,未查到编程语言为{} 待编译语言为{} 的题目", language, compLanguage);
            throw new BizException(BizExceptionCodeEnum.LEXER_PROBLEM_NOT_FOUNT_ERROR);
        }
        LambdaQueryWrapper<LexerTestcasePO> queryWrapper2 = Wrappers.<LexerTestcasePO>lambdaQuery()
                .select(LexerTestcasePO::getLexerId, LexerTestcasePO::getTerminalInput, LexerTestcasePO::getTerminalOutput)
                .eq(LexerTestcasePO::getIsDelete, Boolean.FALSE)
                .eq(LexerTestcasePO::getLexerId, lexerPO.getId())
                .orderByAsc(LexerTestcasePO::getId)
                .last("limit 1");
        LexerTestcasePO lexerTestcasePO = lexerTestcaseRepository.getOne(queryWrapper2);
        if (Objects.isNull(lexerTestcasePO)) {
            log.warn("词法分析器题目获取,未查到编程语言为{} 待编译语言为{} 的用例", language, compLanguage);
            throw new BizException(BizExceptionCodeEnum.LEXER_TESTCASE_NOT_FOUNT_ERROR);
        }
        LexerProblemResponseDTO lexerProblemResponseDTO = ojConvert.lexerProblemConvert(lexerTestcasePO);
        lexerProblemResponseDTO.setDescription(lexerPO.getDescription());
        return lexerProblemResponseDTO;
    }

    @Override
    @Transactional
    public CodeReviewResponseDTO checkLexerCode(Long lexerId, String code) {
        // 提交截止则直接返回
        ConfigPO config = configMapper.getConfig();
        if (config.getLexerDeadline().isBefore(LocalDateTime.now())) {
            log.info("词法分析器代码校验,考试已截止");
            throw new BizException(BizExceptionCodeEnum.LEXER_EXAM_CLOSED_ERROR);
        }
        // 获取测试用例
        List<LexerTestCaseDTO> lexerTestCaseDTOList;
        String lexerKey = lexerTestcasePrefix + lexerId.toString();
        if (redisUtil.lexerKeyExists(lexerKey)) {
            lexerTestCaseDTOList = redisUtil.lexerTestCaseGet(lexerKey);
        } else {
            LambdaQueryWrapper<LexerTestcasePO> queryWrapper = Wrappers.<LexerTestcasePO>lambdaQuery()
                    .select(LexerTestcasePO::getTerminalInput, LexerTestcasePO::getTerminalOutput)
                    .eq(LexerTestcasePO::getIsDelete, Boolean.FALSE)
                    .eq(LexerTestcasePO::getLexerId, lexerId);
            lexerTestCaseDTOList = ojConvert.lexerTestCaseConvert(lexerTestcaseRepository.list(queryWrapper));
            redisUtil.lexerListPut(lexerKey, lexerTestCaseDTOList);
        }
        // 调用编译器校验&评分
        // 通过标志，若有一个错误则为半对
        boolean isAccept = true;
        // 通过用例数，用于评分
        int passedCase = 0;
        LambdaQueryWrapper<LexerPO> lexerQueryWrapper = Wrappers.<LexerPO>lambdaQuery()
                .select(LexerPO::getLanguage)
                .eq(LexerPO::getIsDelete, Boolean.FALSE)
                .eq(LexerPO::getId, lexerId);
        LexerPO lexerPO = lexerRepository.getOne(lexerQueryWrapper);
        String language = lexerPO.getLanguage();
        Iterator<LexerTestCaseDTO> iterator = lexerTestCaseDTOList.iterator();
        log.info("校验词法分析器代码,待校验的代码\n {}", code);
        while (iterator.hasNext()) {
            // 获取一个用例
            LexerTestCaseDTO next = iterator.next();
            CodeReviewResponseDTO compileError = compile2("词法分析器编译", code, next.getTerminalInput(), next.getTerminalOutput(), language);
            if (Objects.nonNull(compileError)) {
                if (compileError.getStatus().equals(CompileStatusEnum.COMPILE_ERROR.getCode())) {
                    return compileError;
                } else {
                    isAccept = false;
                }
            } else {
                passedCase++;
            }
        }
        Integer score = passedCase * LexerTestcaseWeight;
        if (score.equals(0)) {
            return new CodeReviewResponseDTO(CompileStatusEnum.PARTIAL_ERROR.getCode(), "未能通过全部测试用例");
        }
        // 代码入库
        Long coderId = idGenerator.generate();
        LexerCodePO lexerCodePO = new LexerCodePO();
        lexerCodePO.setId(coderId);
        lexerCodePO.setCode(sourceCodeUtil.toSqlCode(code));
        lexerCodeRepository.save(lexerCodePO);
        // 成绩入库
        Long userId = UserContextHolder.getUserId();
        LambdaQueryWrapper<StudentPO> queryWrapper = Wrappers.<StudentPO>lambdaQuery()
                .select(StudentPO::getLexerScore, StudentPO::getId)
                .eq(StudentPO::getIsDelete, Boolean.FALSE)
                .eq(StudentPO::getUserId, userId);
        StudentPO studentPO = studentRepository.getOne(queryWrapper);
        if (Objects.isNull(studentPO)) {
            log.error("词法分析器题代码校验,学生用户信息不存在,userId = {}", userId);
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
        LexerAnswerPO lexerAnswer = getLexerAnswer(userId, lexerId);
        if (Objects.isNull(lexerAnswer)) {
            lexerAnswer = new LexerAnswerPO();
            lexerAnswer.setId(idGenerator.generate());
            lexerAnswer.setLastCodeId(coderId);
            lexerAnswer.setBestCodeId(coderId);
            lexerAnswer.setUserId(userId);
            lexerAnswer.setScore(score);
            lexerAnswer.setLexerId(lexerId);
            lexerAnswerRepository.save(lexerAnswer);
            studentPO.setLexerScore(score);
            studentMapper.updateStudentInfo(studentPO);
        } else {
            Integer grade = lexerAnswer.getScore();
            lexerAnswer.setLastCodeId(coderId);
            // 空值不更新
            lexerAnswer.setScore(null);
            lexerAnswer.setBestCodeId(null);
            if (grade < score) {
                lexerAnswer.setBestCodeId(coderId);
                lexerAnswer.setScore(score);
                studentPO.setLexerScore(score);
                studentMapper.updateStudentInfo(studentPO);
            }
            lexerAnswerMapper.updateLexerAnswer(lexerAnswer);
        }
        if (isAccept) {
            return new CodeReviewResponseDTO(CompileStatusEnum.SUCCESS.getCode(), "通过全部用例");
        } else {
            return new CodeReviewResponseDTO(CompileStatusEnum.PARTIAL_ERROR.getCode(), "未能通过全部测试用例");
        }
    }

    @Override
    public Map<String, List<String>> getLexerLanguage() {
        LambdaQueryWrapper<LexerPO> queryWrapper = Wrappers.<LexerPO>lambdaQuery()
                .select(LexerPO::getLanguage, LexerPO::getCompLanguage)
                .eq(LexerPO::getIsDelete, Boolean.FALSE);
        HashMap<String, List<String>> languageHashMap = new HashMap<>();
        lexerRepository.list(queryWrapper).forEach(lexerPO -> languageHashMap
                .computeIfAbsent(lexerPO.getCompLanguage(), k -> new ArrayList<>())
                .add(lexerPO.getLanguage()));
        return languageHashMap;
    }

    @Override
    public LexerLanguageResponseDTO getLexerLanguage2() {
        LambdaQueryWrapper<LexerPO> queryWrapper = Wrappers.<LexerPO>lambdaQuery()
                .select(LexerPO::getId, LexerPO::getLanguage, LexerPO::getCompLanguage)
                .eq(LexerPO::getIsDelete, Boolean.FALSE);
        List<LexerLanguageResponseDTO.LanguageMap> list = lexerRepository.list(queryWrapper).stream().map(item -> {
            LexerLanguageResponseDTO.LanguageMap languageMap = new LexerLanguageResponseDTO.LanguageMap();
            languageMap.setLexerId(item.getId().toString());
            languageMap.setCompLanguage(item.getCompLanguage());
            languageMap.setLanguageList(Collections.singletonList(item.getLanguage()));
            return languageMap;
        }).toList();
        LexerLanguageResponseDTO responseDTO = new LexerLanguageResponseDTO();
        responseDTO.setLanguageMaps(list);
        return responseDTO;
    }

    @Override
    public LexerProblemResponseDTO getLexerProblem(String lexerId) {
        LambdaQueryWrapper<LexerPO> queryWrapper = Wrappers.<LexerPO>lambdaQuery()
                .select(LexerPO::getDescription)
                .eq(LexerPO::getIsDelete, Boolean.FALSE)
                .eq(LexerPO::getId, lexerId);
        LexerPO lexerPO = lexerRepository.getOne(queryWrapper);
        LambdaQueryWrapper<LexerTestcasePO> queryWrapper2 = Wrappers.<LexerTestcasePO>lambdaQuery()
                .select(LexerTestcasePO::getLexerId, LexerTestcasePO::getTerminalInput, LexerTestcasePO::getTerminalOutput)
                .eq(LexerTestcasePO::getIsDelete, Boolean.FALSE)
                .eq(LexerTestcasePO::getLexerId, lexerId)
                .orderByAsc(LexerTestcasePO::getId)
                .last("limit 1");
        LexerTestcasePO lexerTestcasePO = lexerTestcaseRepository.getOne(queryWrapper2);
        if (Objects.isNull(lexerTestcasePO)) {
            log.warn("词法分析器题目获取,未查到lexerId = {} 的用例", lexerId);
            throw new BizException(BizExceptionCodeEnum.LEXER_TESTCASE_NOT_FOUNT_ERROR);
        }
        LexerProblemResponseDTO lexerProblemResponseDTO = ojConvert.lexerProblemConvert(lexerTestcasePO);
        lexerProblemResponseDTO.setDescription(lexerPO.getDescription());
        return lexerProblemResponseDTO;
    }

    @Override
    public LexerCodeReviewResponseDTO getLexerAnswer(LexerReviewRequestDTO requestDTO) {
        // 获取学生基本信息
        LambdaQueryWrapper<StudentPO> queryWrapper = Wrappers.<StudentPO>lambdaQuery()
                .eq(StudentPO::getIsDelete, Boolean.FALSE)
                .eq(StudentPO::getNumber, requestDTO.getNumber());
        StudentPO studentPO = studentRepository.getOne(queryWrapper);
        if (Objects.isNull(studentPO)) {
            log.info("查询学生词法分析器作答情况,学生不存在, student number = {}", requestDTO.getNumber());
            throw new BizException(BizExceptionCodeEnum.STUDENT_NOT_EXIST_ERROR);
        }
        // 校验是否为所属教学班学生
        if (!getClassList().contains(studentPO.getTeachClass())) {
            log.info("查询学生词法分析器作答情况,学生非所属教学班, student number = {}", requestDTO.getNumber());
            throw new BizException(BizExceptionCodeEnum.CLASS_NO_ACCESS_ERROR);
        }
        LexerCodeReviewResponseDTO responseDTO = new LexerCodeReviewResponseDTO();
        StudentBaseInfoResponseDTO baseInfoDTO = new StudentBaseInfoResponseDTO();
        baseInfoDTO.setName(studentPO.getName());
        baseInfoDTO.setNumber(String.valueOf(studentPO.getNumber()));
        baseInfoDTO.setAdminClass(studentPO.getAdminClass());
        baseInfoDTO.setTeachClass(studentPO.getTeachClass());
        responseDTO.setBaseInfo(baseInfoDTO);
        // 获取答题情况
        LexerAnswerPO lexerAnswerPO = getLexerAnswer(studentPO.getUserId(), Long.parseLong(requestDTO.getLexer_id()));
        if (Objects.isNull(lexerAnswerPO) || Objects.isNull(lexerAnswerPO.getBestCodeId()) || 0L == lexerAnswerPO.getBestCodeId()) {
            responseDTO.setScore(0);
            return responseDTO;
        }
        LambdaQueryWrapper<LexerCodePO> queryWrapper2 = Wrappers.<LexerCodePO>lambdaQuery()
                .select(LexerCodePO::getCode)
                .eq(LexerCodePO::getIsDelete, Boolean.FALSE)
                .eq(LexerCodePO::getId, lexerAnswerPO.getBestCodeId());
        LexerCodePO codePO = lexerCodeRepository.getOne(queryWrapper2);
        if (Objects.isNull(codePO)) {
            log.error("查询代码, student number = {}, codeId = {} 代码不存在", requestDTO.getNumber(), lexerAnswerPO.getBestCodeId());
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
        String sourceCode = codePO.getCode();
        // 将代码字符串转化为字符串列表形式
        List<String> codeLine = sourceCodeUtil.toCodeLine(sourceCode);
        SourceCodeResponseDTO sourceCodeResponseDTO = new SourceCodeResponseDTO();
        sourceCodeResponseDTO.setCode(codeLine);
        responseDTO.setSourceCode(sourceCodeResponseDTO);
        responseDTO.setScore(studentPO.getLexerScore());
        return responseDTO;
    }

    @Override
    public SourceCodeResponseDTO getLastCommitCode(String lexerId) {
        // 提交截止则直接返回
        ConfigPO config = configMapper.getConfig();
        if (config.getLexerDeadline().isBefore(LocalDateTime.now())) {
            log.info("查询词法分析器最近提交代码,考试已截止");
            throw new BizException(BizExceptionCodeEnum.LEXER_EXAM_CLOSED_ERROR);
        }
        // 查询学生最新提交记录代码
        Long userId = UserContextHolder.getUserId();
        LexerAnswerPO lexerAnswerPO = getLexerAnswer(userId, Long.parseLong(lexerId));
        if (Objects.isNull(lexerAnswerPO)) {
            return null;
        }
        LambdaQueryWrapper<LexerCodePO> queryWrapper2 = Wrappers.<LexerCodePO>lambdaQuery()
                .select(LexerCodePO::getCode)
                .eq(LexerCodePO::getIsDelete, Boolean.FALSE)
                .eq(LexerCodePO::getId, lexerAnswerPO.getLastCodeId());
        LexerCodePO codePO = lexerCodeRepository.getOne(queryWrapper2);
        if (Objects.isNull(codePO)) {
            log.error("查询最新提交代码, userId = {}, codeId = {} 代码不存在", userId, lexerAnswerPO.getLastCodeId());
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
        String sourceCode = codePO.getCode();
        // 将代码字符串转化为字符串列表形式
        List<String> codeLine = sourceCodeUtil.toCodeLine(sourceCode);
        SourceCodeResponseDTO sourceCodeResponseDTO = new SourceCodeResponseDTO();
        sourceCodeResponseDTO.setCode(codeLine);
        return sourceCodeResponseDTO;
    }

    @Override
    @Transactional
    public void plagiarismDetectionPre() {
        // 查询此次需要构建查重代码映射的代码
        ConfigPO config = configMapper.getConfig();
        List<LexerCodePO> bestCodeList = lexerCodeMapper.getBestCode(config.getLexerId(), lexerPDNumber);
        if (CollectionUtils.isEmpty(bestCodeList)) {
            log.info("构建查重代码映射,无需要构建映射的源代码");
            return;
        }
        // todo 代码行入库
        // 构建代码行映射并入库
        AtomicInteger count = new AtomicInteger(0);
        bestCodeList.forEach(item -> {
            String sqlCodeMap = sourceCodeUtil.toSqlCodeMap(item.getCode());
            LambdaUpdateWrapper<LexerCodePO> updateWrapper = Wrappers.<LexerCodePO>lambdaUpdate()
                    .set(LexerCodePO::getCodeMap, sqlCodeMap)
                    .eq(LexerCodePO::getIsDelete, Boolean.FALSE)
                    .eq(LexerCodePO::getId, item.getId());
            lexerCodeRepository.update(updateWrapper);
            count.incrementAndGet();
        });
        log.info("构建查重代码映射,共处理 {} 串代码", count.get());
    }

    @Override
    public LexerPDInfoResponseDTO getPdInfo() {
        ConfigPO configPO = configMapper.getConfig();
        List<LexerPDInfoResponseDTO.PDInfo> pdInfoList = getClassList().stream().map(item -> {
            LexerPDInfoResponseDTO.PDInfo pdInfo = new LexerPDInfoResponseDTO.PDInfo();
            pdInfo.setTeachClass(item);
            LambdaQueryWrapper<LexerPDPO> queryWrapper = Wrappers.<LexerPDPO>lambdaQuery()
                    .eq(LexerPDPO::getIsDelete, Boolean.FALSE)
                    .eq(LexerPDPO::getLexerId, configPO.getLexerId())
                    .eq(LexerPDPO::getTeachClass, item)
                    .ge(LexerPDPO::getRate, configPO.getLexerPdRate());
            pdInfo.setPlagiarismNum((int) lexerPDRepository.count(queryWrapper));
            return pdInfo;
        }).toList();
        LexerPDInfoResponseDTO responseDTO = new LexerPDInfoResponseDTO();
        responseDTO.setPdInfoList(pdInfoList);
        String compLanguage = lexerRepository.getOne(Wrappers.<LexerPO>lambdaQuery()
                .select(LexerPO::getCompLanguage)
                .eq(LexerPO::getIsDelete, Boolean.FALSE)
                .eq(LexerPO::getId, configPO.getLexerId())
        ).getCompLanguage();
        responseDTO.setCompLanguage(compLanguage);
        return responseDTO;
    }

    @Override
    public Integer lexerCodePD(String teachClass) {
        // 参数校验
        if (!getClassList().contains(teachClass)) {
            log.info("词法分析器题代码查重,非所属教学班, teachClass = {}", teachClass);
            throw new BizException(BizExceptionCodeEnum.CLASS_NO_ACCESS_ERROR);
        }
        Long userId = UserContextHolder.getUserId();
        // 获取分布式锁（粒度为教学班）
        boolean locked = lock.lock(lexerCodePDLockKeyPrefix + teachClass, userId.toString(), lexerCodePDLockTimeout);
        if (!locked) {
            log.info("词法分析器题代码查重,当前教学班正在查重, teachClass = {}", teachClass);
            throw new BizException(BizExceptionCodeEnum.LEXER_CODE_PD_LOCKED_ERROR);
        }
        // 查询代码映射是否就绪
        ConfigPO configPO = configMapper.getConfig();
        if (!CollectionUtils.isEmpty(lexerCodeMapper.getBestCode(configPO.getLexerId(), Constants.ONE))) {
            log.info("词法分析器题代码查重,查重还未就绪");
            lock.unlock(lexerCodePDLockKeyPrefix + teachClass, userId.toString());
            throw new BizException(BizExceptionCodeEnum.LEXER_CODE_PD_PREPARING_ERROR);
        }
        // 查询对应教学班的所有待查重代码
        LexerPDCodeQueryDTO queryDTO = new LexerPDCodeQueryDTO();
        queryDTO.setLexerId(configPO.getLexerId());
        queryDTO.setTeachClass(teachClass);
        List<Long> codeIdList = lexerAnswerMapper.getPDCodeId(queryDTO);
        if (CollectionUtils.isEmpty(codeIdList) || codeIdList.size() == Constants.ONE) {
            log.warn("词法分析器题代码查重,当前教学班无可查重代码,teach class = {}", teachClass);
            lock.unlock(lexerCodePDLockKeyPrefix + teachClass, userId.toString());
            throw new BizException(BizExceptionCodeEnum.LEXER_NO_RECORDED_CODE_ERROR);
        }
        // 查重任务异步进行
        threadPoolUtil.submitTask(() -> {
            log.info("词法分析器题代码查重任务开始执行, teach class = {}", teachClass);
            long startTime = System.currentTimeMillis();
            try {
                // 开始查重
                LambdaQueryWrapper<LexerCodePO> queryWrapper2 = Wrappers.<LexerCodePO>lambdaQuery()
                        .select(LexerCodePO::getCodeMap, LexerCodePO::getId)
                        .eq(LexerCodePO::getIsDelete, Boolean.FALSE)
                        .in(LexerCodePO::getId, codeIdList);
                List<LexerCodePO> lexerCodeList = lexerCodeRepository.list(queryWrapper2);
                int num = lexerCodeList.size();
                List<LexerPDPO> lexerPDList = new ArrayList<>(num * (num - 1));
                for (int i = 0; i < num; i++) {
                    LexerCodePO pdCodePO = lexerCodeList.get(i);
                    // 排除此前已对比的代码
                    LambdaQueryWrapper<LexerPDPO> queryWrapper3 = Wrappers.<LexerPDPO>lambdaQuery()
                            .select(LexerPDPO::getCompCodeId)
                            .eq(LexerPDPO::getIsDelete, Boolean.FALSE)
                            .eq(LexerPDPO::getLexerId, configPO.getLexerId())
                            .eq(LexerPDPO::getPlagiarismCodeId, pdCodePO.getId());
                    Set<Long> existedPDCodeIdSet = lexerPDRepository.list(queryWrapper3).stream().map(LexerPDPO::getCompCodeId).collect(Collectors.toSet());
                    String[] pdMapList = sourceCodeUtil.mapStr2mapList(pdCodePO.getCodeMap());
                    for (int j = 0; j < num; j++) {
                        LexerCodePO curCodePO = lexerCodeList.get(j);
                        if (i == j || existedPDCodeIdSet.contains(curCodePO.getId())) {
                            continue;
                        }
                        // 计算查重率
                        Set<String> compSet = new HashSet<>(List.of(sourceCodeUtil.mapStr2mapList(curCodePO.getCodeMap())));
                        int pdCount = 0;
                        for (String line : pdMapList) {
                            if (compSet.contains(line)) {
                                pdCount++;
                            }
                        }
                        double pdRate = pdCount * 1.0 / pdMapList.length * 100;
                        LexerPDPO lexerPDPO = new LexerPDPO();
                        lexerPDPO.setId(idGenerator.generate());
                        lexerPDPO.setLexerId(configPO.getLexerId());
                        lexerPDPO.setCompCodeId(curCodePO.getId());
                        lexerPDPO.setTeachClass(teachClass);
                        lexerPDPO.setRate(new BigDecimal(pdRate));
                        lexerPDPO.setPlagiarismCodeId(pdCodePO.getId());
                        lexerPDList.add(lexerPDPO);
                    }
                }
                // 查重结果入库
                lexerPDRepository.saveBatch(lexerPDList);
            } catch (RejectedExecutionException exception) {
                log.warn("当前异步任务线程池已满, 抛弃查重任务, teach class = {}", teachClass);
                throw new BizException(BizExceptionCodeEnum.THREAD_POOL_FULL_ERROR);
            } finally {
                lock.unlock(lexerCodePDLockKeyPrefix + teachClass, userId.toString());
            }
            log.info("词法分析器题代码查重任务执行完成, teach class = {}, 用时 {}ms", teachClass, System.currentTimeMillis() - startTime);
        });
        return codeIdList.size();
    }

    @Override
    public LexerPlaStudentInfoResponseDTO getPlaStudentInfo(String teachClass) {
        if (!getClassList().contains(teachClass)) {
            log.info("词法分析器题代码抄袭学生导出,非所属教学班, teachClass = {}", teachClass);
            throw new BizException(BizExceptionCodeEnum.CLASS_NO_ACCESS_ERROR);
        }
        ConfigPO configPO = configMapper.getConfig();
        LambdaQueryWrapper<LexerPDPO> queryWrapper = Wrappers.<LexerPDPO>lambdaQuery()
                .select(LexerPDPO::getPlagiarismCodeId, LexerPDPO::getCompCodeId)
                .eq(LexerPDPO::getIsDelete, Boolean.FALSE)
                .eq(LexerPDPO::getLexerId, configPO.getLexerId())
                .eq(LexerPDPO::getTeachClass, teachClass)
                .ge(LexerPDPO::getRate, configPO.getLexerPdRate());
        List<LexerPDPO> lexerPDPOList = lexerPDRepository.list(queryWrapper);
        if (CollectionUtils.isEmpty(lexerPDPOList)) {
            return new LexerPlaStudentInfoResponseDTO();
        }
        Set<Long> codeIdSet = new HashSet<>();
        for (LexerPDPO lexerPDPO : lexerPDPOList) {
            codeIdSet.add(lexerPDPO.getCompCodeId());
            codeIdSet.add(lexerPDPO.getPlagiarismCodeId());
        }
        List<StudentPO> studentPOList = studentMapper.getStudentsByCodeIds(codeIdSet);
        LexerPlaStudentInfoResponseDTO responseDTO = new LexerPlaStudentInfoResponseDTO();
        responseDTO.setStudentList(ojConvert.convert(studentPOList));
        return responseDTO;
    }

    @Override
    public LexerPageQueryResponseDTO pageQueryLexer(LexerPageQueryRequestDTO requestDTO) {
        LambdaQueryWrapper<LexerPO> queryWrapper = Wrappers.<LexerPO>lambdaQuery()
                .eq(LexerPO::getIsDelete, Boolean.FALSE);
        if (StringUtils.isNotBlank(requestDTO.getLanguage())) {
            queryWrapper.eq(LexerPO::getLanguage, requestDTO.getLanguage());
        }
        if (StringUtils.isNotBlank(requestDTO.getCompLanguage())) {
            queryWrapper.eq(LexerPO::getCompLanguage, requestDTO.getCompLanguage());
        }
        if (StringUtils.isNotBlank(requestDTO.getDescription())) {
            queryWrapper.like(LexerPO::getDescription, requestDTO.getDescription());
        }
        queryWrapper.orderByDesc(LexerPO::getId);
        Page<LexerPO> pageQuery = new Page<>();
        pageQuery.setCurrent(requestDTO.getPageIndex());
        pageQuery.setSize(requestDTO.getPageSize());
        Page<LexerPO> page = lexerRepository.page(pageQuery, queryWrapper);
        LexerPageQueryResponseDTO responseDTO = new LexerPageQueryResponseDTO();
        responseDTO.setCurrentPage((int) page.getCurrent());
        responseDTO.setTotalPages((int) page.getPages());
        responseDTO.setTotalRecords((int) page.getTotal());
        responseDTO.setLexerList(ojConvert.convertList(page.getRecords()));
        return responseDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveLexer(LexerSaveRequestDTO requestDTO) {
        if (Objects.nonNull(requestDTO.getId())) {
            // 更新
            LambdaQueryWrapper<LexerPO> queryWrapper = Wrappers.<LexerPO>lambdaQuery()
                    .select(LexerPO::getId)
                    .eq(LexerPO::getIsDelete, Boolean.FALSE)
                    .eq(LexerPO::getId, requestDTO.getId());
            LexerPO lexerPO = lexerRepository.getOne(queryWrapper);
            if (Objects.isNull(lexerPO)) {
                log.warn("更新词法分析器题,词法分析器id不存在,id = {}", requestDTO.getId());
                throw new BizException(BizExceptionCodeEnum.LEXER_PROBLEM_NOT_FOUNT_ERROR);
            }
            lexerPO = ojConvert.convert(requestDTO);
            Integer updated = lexerMapper.updateLexer(lexerPO);
            if (Objects.equals(updated, Constants.ZERO)) {
                log.warn("更新词法分析器题，未更新成功,id = {}", requestDTO.getId());
                throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
            }
            LambdaQueryWrapper<LexerTestcasePO> queryWrapper1 = Wrappers.<LexerTestcasePO>lambdaQuery()
                    .select(LexerTestcasePO::getId)
                    .eq(LexerTestcasePO::getIsDelete, Boolean.FALSE)
                    .eq(LexerTestcasePO::getLexerId, lexerPO.getId())
                    .orderByAsc(LexerTestcasePO::getId)
                    .last("limit 1");
            LexerTestcasePO lexerTestcasePO = lexerTestcaseRepository.getOne(queryWrapper1);
            if (Objects.isNull(lexerTestcasePO)) {
                log.warn("更新词法分析器题，无初始用例,id = {}", lexerPO.getId());
                throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
            }
            LambdaUpdateWrapper<LexerTestcasePO> updateWrapper = Wrappers.<LexerTestcasePO>lambdaUpdate()
                    .set(LexerTestcasePO::getTerminalInput, requestDTO.getTerminalInput())
                    .set(LexerTestcasePO::getTerminalOutput, requestDTO.getTerminalOutput())
                    .eq(LexerTestcasePO::getIsDelete, Boolean.FALSE)
                    .eq(LexerTestcasePO::getId, lexerTestcasePO.getId());
            boolean updated1 = lexerTestcaseRepository.update(updateWrapper);
            if (Objects.equals(updated1, Boolean.FALSE)) {
                log.warn("更新词法分析器题，用例未更新成功,lexer id = {},testcase id = {}", requestDTO.getId(), lexerTestcasePO.getId());
                throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
            }
            return lexerPO.getId();
        }
        // 新增
        LexerPO lexerPO = ojConvert.convert(requestDTO);
        long lexerId = idGenerator.generate();
        lexerPO.setId(lexerId);
        boolean saved = lexerRepository.save(lexerPO);
        if (Objects.equals(saved, Boolean.FALSE)) {
            log.warn("新增词法分析器题，未添加成功,id = {}", requestDTO.getId());
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
        LexerTestcasePO lexerTestcasePO = new LexerTestcasePO();
        lexerTestcasePO.setId(idGenerator.generate());
        lexerTestcasePO.setLexerId(lexerId);
        lexerTestcasePO.setTerminalInput(requestDTO.getTerminalInput());
        lexerTestcasePO.setTerminalOutput(requestDTO.getTerminalOutput());
        boolean saved1 = lexerTestcaseRepository.save(lexerTestcasePO);
        if (Objects.equals(saved1, Boolean.FALSE)) {
            log.warn("新增词法分析器题，未成功添加示例用例,lexer id = {}", lexerId);
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
        return lexerId;
    }

    @Override
    public LexerDetailResponseDTO getLexerDetail(Long lexerId) {
        LambdaQueryWrapper<LexerPO> queryWrapper = Wrappers.<LexerPO>lambdaQuery()
                .select(LexerPO::getId, LexerPO::getLanguage, LexerPO::getCompLanguage)
                .eq(LexerPO::getIsDelete, Boolean.FALSE)
                .eq(LexerPO::getId, lexerId);
        LexerPO lexerPO = lexerRepository.getOne(queryWrapper);
        if (Objects.isNull(lexerPO)) {
            log.warn("查询词法分析器题详情,词法分析器id不存在,id = {}", lexerId);
            throw new BizException(BizExceptionCodeEnum.LEXER_PROBLEM_NOT_FOUNT_ERROR);
        }
        LambdaQueryWrapper<LexerTestcasePO> queryWrapper1 = Wrappers.<LexerTestcasePO>lambdaQuery()
                .select(LexerTestcasePO::getTerminalInput, LexerTestcasePO::getTerminalOutput)
                .eq(LexerTestcasePO::getIsDelete, Boolean.FALSE)
                .eq(LexerTestcasePO::getLexerId, lexerPO.getId())
                .orderByAsc(LexerTestcasePO::getId)
                .last("limit 1");
        LexerTestcasePO lexerTestcasePO = lexerTestcaseRepository.getOne(queryWrapper1);
        LexerDetailResponseDTO responseDTO = ojConvert.convert(lexerPO);
        responseDTO.setTerminalInput(lexerTestcasePO.getTerminalInput());
        responseDTO.setTerminalOutput(lexerTestcasePO.getTerminalOutput());
        return responseDTO;
    }

    @Override
    public LexerTestcasePageResponseDTO pageLexerTestcase(LexerTestcasePageRequestDTO requestDTO) {
        lexerExistVerify(requestDTO.getLexerId(), "分页查询词法分析器题用例");
        LambdaQueryWrapper<LexerTestcasePO> queryWrapper1 = Wrappers.<LexerTestcasePO>lambdaQuery()
                .select(LexerTestcasePO::getTerminalInput, LexerTestcasePO::getTerminalOutput)
                .eq(LexerTestcasePO::getIsDelete, Boolean.FALSE)
                .eq(LexerTestcasePO::getLexerId, requestDTO.getLexerId())
                .orderByDesc(LexerTestcasePO::getId);
        Page<LexerTestcasePO> queryPage = new Page<>();
        queryPage.setCurrent(requestDTO.getPageIndex())
                .setSize(requestDTO.getPageSize());
        Page<LexerTestcasePO> page = lexerTestcaseRepository.page(queryPage, queryWrapper1);
        List<LexerTestcasePageResponseDTO.Testcase> testcaseList = ojConvert.convertList2(page.getRecords());
        LexerTestcasePageResponseDTO responseDTO = new LexerTestcasePageResponseDTO();
        responseDTO.setList(testcaseList);
        responseDTO.setCurrentPage((int) page.getCurrent());
        responseDTO.setTotalPages((int) page.getPages());
        responseDTO.setTotalRecords((int) page.getTotal());
        return responseDTO;
    }

    @Override
    public void addLexerTestcase(LexerTestcaseAddRequestDTO requestDTO) {
        String logStr = "添加词法分析器题用例";
        lexerExistVerify(requestDTO.getLexerId(), logStr);
        LexerTestcasePO lexerTestcasePO = new LexerTestcasePO();
        lexerTestcasePO.setId(idGenerator.generate());
        lexerTestcasePO.setLexerId(requestDTO.getLexerId());
        lexerTestcasePO.setTerminalInput(requestDTO.getTerminalInput());
        lexerTestcasePO.setTerminalOutput(requestDTO.getTerminalOutput());
        boolean saved = lexerTestcaseRepository.save(lexerTestcasePO);
        if (Objects.equals(saved, Boolean.FALSE)) {
            log.error(logStr + ",用例添加失败, lexerId = {}", requestDTO.getLexerId());
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public void deleteLexerTestcase(Long id) {
        // todo 无法删除初始用例
        // todo 词法分析器题保存初始用例id
        LambdaUpdateWrapper<LexerTestcasePO> updateWrapper = Wrappers.<LexerTestcasePO>lambdaUpdate()
                .set(LexerTestcasePO::getIsDelete, Boolean.TRUE)
                .eq(LexerTestcasePO::getIsDelete, Boolean.FALSE)
                .eq(LexerTestcasePO::getId, id);
        boolean updated = lexerTestcaseRepository.update(updateWrapper);
        if (Objects.equals(updated, Boolean.FALSE)) {
            log.error("删除词法分析器题用例，用例删除失败, id = {}", id);
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public SourceCodeResponseDTO aiCodeGenerate(AiCodeGenerateRequestDTO requestDTO) {
        return null;
    }

    /**
     * 校验词法分析器题是否存在
     *
     * @param lexerId 词法分析器题id
     * @param logStr  日志前缀
     */
    private void lexerExistVerify(Long lexerId, String logStr) {
        LambdaQueryWrapper<LexerPO> queryWrapper = Wrappers.<LexerPO>lambdaQuery()
                .select(LexerPO::getId)
                .eq(LexerPO::getIsDelete, Boolean.FALSE)
                .eq(LexerPO::getId, lexerId);
        LexerPO lexerPO = lexerRepository.getOne(queryWrapper);
        if (Objects.isNull(lexerPO)) {
            log.warn(logStr + ",词法分析器题id不存在,id = {}", lexerId);
            throw new BizException(BizExceptionCodeEnum.LEXER_PROBLEM_NOT_FOUNT_ERROR);
        }
    }

    /**
     * 获取教师所带教学班列表
     *
     * @return 教师所带教学班列表
     */
    private List<String> getClassList() {
        Long userId = UserContextHolder.getUserId();
        LambdaQueryWrapper<TeacherPO> queryWrapper = Wrappers.<TeacherPO>lambdaQuery()
                .select(TeacherPO::getClassList)
                .eq(TeacherPO::getIsDelete, Boolean.FALSE)
                .eq(TeacherPO::getUserId, userId);
        return Arrays.asList(teacherRepository.getOne(queryWrapper).getClassList().split(teachClassDelimiter, -1));
    }

    /**
     * 获取学生词法分析器题成绩
     *
     * @return 词法分析器题成绩信息
     */
    private LexerAnswerPO getLexerAnswer(Long userId, Long lexerId) {
        LambdaQueryWrapper<LexerAnswerPO> queryWrapper = Wrappers.<LexerAnswerPO>lambdaQuery()
                .select(LexerAnswerPO::getId, LexerAnswerPO::getLastCodeId, LexerAnswerPO::getBestCodeId, LexerAnswerPO::getScore)
                .eq(LexerAnswerPO::getIsDelete, Boolean.FALSE)
                .eq(LexerAnswerPO::getUserId, userId)
                .eq(LexerAnswerPO::getLexerId, lexerId);
        return lexerAnswerRepository.getOne(queryWrapper);
    }

    /**
     * 发送编译请求
     *
     * @param code           待编译的源代码
     * @param terminalInput  终端输入
     * @param terminalOutput 终端输出
     * @param language       编程的语言
     * @return 编译报错信息
     */
    private CodeReviewResponseDTO compile2(String prefix, String code, String terminalInput, String terminalOutput, String language) {
        // 发送请求编译
        CompilationInputDTO compilationInputDTO = new CompilationInputDTO(code, terminalInput, language, null);
        CompletableFuture<CompilationOutputDTO> future = CompletableFuture.supplyAsync(() -> remoteCompilerFacade.compile2(compilationInputDTO));
        try {
            CompilationOutputDTO compilationOutputDTO = future.get();
            // 匹配校验信息
            if (compilationOutputDTO.getCompilationError()) {
                return new CodeReviewResponseDTO(CompileStatusEnum.COMPILE_ERROR.getCode(), "编译出错");
            }
            //todo 优化校验

            if (!check(compilationOutputDTO.getOutput(), terminalOutput)) {
                return new CodeReviewResponseDTO(CompileStatusEnum.PARTIAL_ERROR.getCode(), "未能通过全部测试用例");
            }
        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                log.error(prefix + ",获取编译结果时任务中断");
            } else if (e instanceof ExecutionException) {
                log.error(prefix + ",获取编译结果失败");
            } else {
                log.error(prefix + ",获取编译结果出现未知错误");
            }
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
        return null;
    }

    /**
     * 匹配校验代码目标输出与当前输出
     */
    private Boolean check(String current, String target) {
//        String trimmedStr1 = current.replaceAll("^[\\s\\p{Cntrl}]+|[\\s\\p{Cntrl}]+$", "");
//        String trimmedStr2 = target.replaceAll("^[\\s\\p{Cntrl}]+|[\\s\\p{Cntrl}]+$", "");
        String trimmedStr1 = current.trim();
        String trimmedStr2 = target.trim();
        return trimmedStr1.equals(trimmedStr2);
    }
}
