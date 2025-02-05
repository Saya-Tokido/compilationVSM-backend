package com.ljz.compilationVSM.domain.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljz.compilationVSM.common.dto.LexerTestCaseDTO;
import com.ljz.compilationVSM.common.enums.CompileStatusEnum;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.common.utils.RedisUtil;
import com.ljz.compilationVSM.common.utils.SnowflakeIdGenerator;
import com.ljz.compilationVSM.common.utils.SourceCodeUtil;
import com.ljz.compilationVSM.common.utils.UserContextHolder;
import com.ljz.compilationVSM.dependency.dto.CompilationInputDTO;
import com.ljz.compilationVSM.dependency.dto.CompilationOutputDTO;
import com.ljz.compilationVSM.dependency.facade.RemoteCompilerFacade;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.StudentBaseInfoResponseDTO;
import com.ljz.compilationVSM.domain.convert.OJDTOMapping;
import com.ljz.compilationVSM.domain.oj.dto.*;
import com.ljz.compilationVSM.domain.oj.service.OJService;
import com.ljz.compilationVSM.infrastructure.mapper.ConfigMapper;
import com.ljz.compilationVSM.infrastructure.mapper.LexerAnswerMapper;
import com.ljz.compilationVSM.infrastructure.mapper.StudentMapper;
import com.ljz.compilationVSM.infrastructure.po.*;
import com.ljz.compilationVSM.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    private final StudentMapper studentMapper;
    private final LexerAnswerMapper lexerAnswerMapper;
    private final ConfigMapper configMapper;
    private final OJDTOMapping ojConvert;
    private final RemoteCompilerFacade remoteCompilerFacade;
    private final RedisUtil redisUtil;
    private final SnowflakeIdGenerator idGenerator;
    private final SourceCodeUtil sourceCodeUtil;

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
            throw new BizException(BizExceptionCodeEnum.LEXER_EXAM_CLOSED);
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
            throw new BizException(BizExceptionCodeEnum.LEXER_PROBLEM_NOT_FOUNT);
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
            throw new BizException(BizExceptionCodeEnum.LEXER_TESTCASE_NOT_FOUNT);
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
            throw new BizException(BizExceptionCodeEnum.LEXER_EXAM_CLOSED);
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
            }else{
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
                .select(StudentPO::getLexerGrade, StudentPO::getId)
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
            lexerAnswer.setGrade(score);
            lexerAnswer.setLexerId(lexerId);
            lexerAnswerRepository.save(lexerAnswer);
            studentPO.setLexerGrade(score);
            studentMapper.updateStudentInfo(studentPO);
        } else {
            Integer grade = lexerAnswer.getGrade();
            lexerAnswer.setLastCodeId(coderId);
            // 空值不更新
            lexerAnswer.setGrade(null);
            lexerAnswer.setBestCodeId(null);
            if (grade < score) {
                lexerAnswer.setBestCodeId(coderId);
                lexerAnswer.setGrade(score);
                studentPO.setLexerGrade(score);
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
            throw new BizException(BizExceptionCodeEnum.LEXER_TESTCASE_NOT_FOUNT);
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
            throw new BizException(BizExceptionCodeEnum.STUDENT_NOT_EXIST);
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
        if (Objects.isNull(lexerAnswerPO)||Objects.isNull(lexerAnswerPO.getBestCodeId()) || 0L == lexerAnswerPO.getBestCodeId()) {
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
        responseDTO.setScore(studentPO.getLexerGrade());
        return responseDTO;
    }

    @Override
    public SourceCodeResponseDTO getLastCommitCode(String lexerId) {
        // 提交截止则直接返回
        ConfigPO config = configMapper.getConfig();
        if (config.getLexerDeadline().isBefore(LocalDateTime.now())) {
            log.info("查询词法分析器最近提交代码,考试已截止");
            throw new BizException(BizExceptionCodeEnum.LEXER_EXAM_CLOSED);
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

    /**
     * 获取学生词法分析器题成绩
     *
     * @return 词法分析器题成绩信息
     */
    private LexerAnswerPO getLexerAnswer(Long userId, Long lexerId) {
        LambdaQueryWrapper<LexerAnswerPO> queryWrapper = Wrappers.<LexerAnswerPO>lambdaQuery()
                .select(LexerAnswerPO::getId, LexerAnswerPO::getLastCodeId, LexerAnswerPO::getBestCodeId, LexerAnswerPO::getGrade)
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
