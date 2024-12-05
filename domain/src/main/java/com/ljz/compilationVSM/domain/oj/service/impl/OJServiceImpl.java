package com.ljz.compilationVSM.domain.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljz.compilationVSM.common.enums.CompileStatusEnum;
import com.ljz.compilationVSM.common.enums.FileextEnum;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.dependency.dto.CompilationInputDTO;
import com.ljz.compilationVSM.dependency.dto.CompilationOutputDTO;
import com.ljz.compilationVSM.dependency.facade.RemoteCompilerFacade;
import com.ljz.compilationVSM.domain.convert.OJDTOMapping;
import com.ljz.compilationVSM.domain.oj.dto.*;
import com.ljz.compilationVSM.domain.oj.service.OJService;
import com.ljz.compilationVSM.infrastructure.po.*;
import com.ljz.compilationVSM.infrastructure.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
@AllArgsConstructor
public class OJServiceImpl implements OJService {

    private final MethodNameRepository methodNameRepository;
    private final MethodBodyRepository methodBodyRepository;
    private final MethodTestcaseRepository methodTestcaseRepository;
    private final LexerRepository lexerRepository;
    private final LexerTestcaseRepository lexerTestcaseRepository;
    private final OJDTOMapping ojConvert;
    private final RemoteCompilerFacade remoteCompilerFacade;

    private final String METHOD_LANGUAGE = "C++";
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
                .eq(MethodTestcasePO::getMethodId,methodId);
        List<MethodTestcasePO> list = methodTestcaseRepository.list(queryWrapper);
        Iterator<MethodTestcasePO> iterator = list.iterator();
        // todo 后期优化为redis list获取
        while (iterator.hasNext()) {
            // 获取一个用例
            MethodTestcasePO next = iterator.next();
            // 拼接
            String compileCode = next.getPreCode() +"\n"+ code;
            log.info("校验函数代码,拼接后的代码\n {}",compileCode);
            CodeReviewResponseDTO compileError = compile2(compileCode, next.getTerminalInput(), next.getTerminalOutput(), METHOD_LANGUAGE);
            if (Objects.nonNull(compileError)) return compileError;
        }
        return new CodeReviewResponseDTO(CompileStatusEnum.SUCCESS.getCode(), "通过全部用例");
    }

    @Override
    public LexerProblemResponseDTO getDemoProblem(String language, String compLanguage) {
        LambdaQueryWrapper<LexerPO> queryWrapper1 = Wrappers.<LexerPO>lambdaQuery()
                .select(LexerPO::getId,LexerPO::getDescription)
                .eq(LexerPO::getIsDelete, Boolean.FALSE)
                .eq(LexerPO::getLanguage, language)
                .eq(LexerPO::getCompLanguage, compLanguage);
        LexerPO lexerPO = lexerRepository.getOne(queryWrapper1);
        if(Objects.isNull(lexerPO)){
            log.warn("词法分析器题目获取,未查到编程语言为{} 待编译语言为{} 的题目",language,compLanguage);
            throw new BizException("未查到该题目类型");
        }
        LambdaQueryWrapper<LexerTestcasePO> queryWrapper2 = Wrappers.<LexerTestcasePO>lambdaQuery()
                .select(LexerTestcasePO::getLexerId, LexerTestcasePO::getTerminalInput, LexerTestcasePO::getTerminalOutput)
                .eq(LexerTestcasePO::getIsDelete, Boolean.FALSE)
                .eq(LexerTestcasePO::getLexerId, lexerPO.getId())
                .orderByAsc(LexerTestcasePO::getId)
                .last("limit 1");
        LexerTestcasePO lexerTestcasePO = lexerTestcaseRepository.getOne(queryWrapper2);
        if(Objects.isNull(lexerTestcasePO)){
            log.warn("词法分析器题目获取,未查到编程语言为{} 待编译语言为{} 的题目",language,compLanguage);
            throw new BizException("未查到该题目类型");
        }
        LexerProblemResponseDTO lexerProblemResponseDTO = ojConvert.lexerProblemConvert(lexerTestcasePO);
        lexerProblemResponseDTO.setDescription(lexerPO.getDescription());
        return lexerProblemResponseDTO;
    }

    @Override
    public CodeReviewResponseDTO checkLexerCode(Long lexerId, String code) {
        LambdaQueryWrapper<LexerPO> lexerQueryWrapper = Wrappers.<LexerPO>lambdaQuery()
                .select(LexerPO::getLanguage)
                .eq(LexerPO::getIsDelete, Boolean.FALSE)
                .eq(LexerPO::getId, lexerId);
        LexerPO lexerPO = lexerRepository.getOne(lexerQueryWrapper);
        String language = lexerPO.getLanguage();
        //todo 从redis获取用例
        LambdaQueryWrapper<LexerTestcasePO> queryWrapper = Wrappers.<LexerTestcasePO>lambdaQuery()
                .select(LexerTestcasePO::getTerminalInput, LexerTestcasePO::getTerminalOutput)
                .eq(LexerTestcasePO::getIsDelete, Boolean.FALSE)
                .eq(LexerTestcasePO::getLexerId,lexerId);
        List<LexerTestcasePO> list = lexerTestcaseRepository.list(queryWrapper);
        Iterator<LexerTestcasePO> iterator = list.iterator();
        log.info("校验词法分析器代码,待校验的代码\n {}",code);
        // todo 后期优化为redis list获取
        while (iterator.hasNext()) {
            // 获取一个用例
            LexerTestcasePO next = iterator.next();
            CodeReviewResponseDTO compileError = compile2(code,next.getTerminalInput(), next.getTerminalOutput(), language);
            if (Objects.nonNull(compileError)) return compileError;
        }
        //todo 待查重
        return new CodeReviewResponseDTO(CompileStatusEnum.SUCCESS.getCode(), "通过全部用例");
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

    /**
     * 发送编译请求
     * @param code 待编译的源代码
     * @param terminalInput 终端输入
     * @param terminalOutput 终端输出
     * @param language 编程的语言
     * @return 编译报错信息
     */
    private CodeReviewResponseDTO compile2(String code, String terminalInput, String terminalOutput, String language) {
        // 发送请求编译
        CompilationInputDTO compilationInputDTO = new CompilationInputDTO(code, terminalInput, language, null);
        CompletableFuture<CompilationOutputDTO> future = CompletableFuture.supplyAsync(() -> remoteCompilerFacade.compile2(compilationInputDTO));
        //todo 后期优化 从redis中获取下一个用例
        try {
            CompilationOutputDTO compilationOutputDTO = future.get();
            // 匹配校验信息
            if (compilationOutputDTO.getCompilationError()) {
                return new CodeReviewResponseDTO(CompileStatusEnum.COMPILE_ERROR.getCode(), "编译出错，请检查是否编写了与当前函数无关内容或修改了函数签名");
            }
            //todo 优化校验

            if (!check(compilationOutputDTO.getOutput(),terminalOutput)) {
                return new CodeReviewResponseDTO(CompileStatusEnum.PARTIAL_ERROR.getCode(), "未能通过全部测试用例");
            }
        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                throw new BizException("获取编译结果时任务中断");
            } else if (e instanceof ExecutionException) {
                throw new BizException("获取编译结果失败");
            } else {
                throw new BizException("获取编译结果出现未知错误");
            }
        }
        return null;
    }

    /**
     * 匹配校验代码目标输出与当前输出
     */
    private Boolean check(String current, String target){
//        String trimmedStr1 = current.replaceAll("^[\\s\\p{Cntrl}]+|[\\s\\p{Cntrl}]+$", "");
//        String trimmedStr2 = target.replaceAll("^[\\s\\p{Cntrl}]+|[\\s\\p{Cntrl}]+$", "");
        String trimmedStr1 = current.replaceAll("\\s+", "");
        String trimmedStr2 = target.replaceAll("\\s+", "");
        return trimmedStr1.equals(trimmedStr2);
    }
}
