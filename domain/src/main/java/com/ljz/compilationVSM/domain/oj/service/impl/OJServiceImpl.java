package com.ljz.compilationVSM.domain.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljz.compilationVSM.common.enums.CompileStatusEnum;
import com.ljz.compilationVSM.common.enums.FileextEnum;
import com.ljz.compilationVSM.common.enums.LanguageEnum;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.dependency.dto.CompilationInputDTO;
import com.ljz.compilationVSM.dependency.dto.CompilationOutputDTO;
import com.ljz.compilationVSM.dependency.facade.RemoteCompilerFacade;
import com.ljz.compilationVSM.domain.convert.OJDTOMapping;
import com.ljz.compilationVSM.domain.oj.dto.CodeReviewResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodBodyResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodListRequestDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodResponseDTO;
import com.ljz.compilationVSM.domain.oj.service.OJService;
import com.ljz.compilationVSM.infrastructure.po.MethodBodyPO;
import com.ljz.compilationVSM.infrastructure.po.MethodNamePO;
import com.ljz.compilationVSM.infrastructure.po.MethodTestcasePO;
import com.ljz.compilationVSM.infrastructure.repository.MethodBodyRepository;
import com.ljz.compilationVSM.infrastructure.repository.MethodNameRepository;
import com.ljz.compilationVSM.infrastructure.repository.MethodTestcaseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
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
    private final OJDTOMapping ojConvert;
    private final RemoteCompilerFacade remoteCompilerFacade;

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
    public CodeReviewResponseDTO checkCode(Long methodId, String code) {
        //todo 从redis获取用例
        LambdaQueryWrapper<MethodTestcasePO> queryWrapper = Wrappers.<MethodTestcasePO>lambdaQuery()
                .select(MethodTestcasePO::getMethodId, MethodTestcasePO::getPreCode, MethodTestcasePO::getTerminalInput, MethodTestcasePO::getTerminalOutput)
                .eq(MethodTestcasePO::getIsDelete, Boolean.FALSE);
        List<MethodTestcasePO> list = methodTestcaseRepository.list(queryWrapper);
        Iterator<MethodTestcasePO> iterator = list.iterator();
        // todo 后期优化为redis list获取
        while (iterator.hasNext()) {
            // 获取一个用例
            MethodTestcasePO next = iterator.next();
            // 拼接
            String compileCode = next.getPreCode() +"\n"+ code;
            log.info("校验代码,拼接后的代码 {}",compileCode);
            // 发送请求编译
            CompilationInputDTO compilationInputDTO = new CompilationInputDTO(compileCode, next.getTerminalInput(), LanguageEnum.CPP.getName(), FileextEnum.CPP.getName());
            CompletableFuture<CompilationOutputDTO> future = CompletableFuture.supplyAsync(() -> remoteCompilerFacade.compile(compilationInputDTO));
            //todo 后期优化 从redis中获取下一个用例
            try {
                CompilationOutputDTO compilationOutputDTO = future.get();
                // 匹配校验信息
                if (compilationOutputDTO.getCompilationError()) {
                    return new CodeReviewResponseDTO(CompileStatusEnum.COMPILE_ERROR.getCode(), "编译出错，请检查是否编写了与当前函数无关内容或修改了函数签名");
                }
                //todo 优化校验
                if (!next.getTerminalOutput().equals(compilationOutputDTO.getOutput())) {
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
        }
        return new CodeReviewResponseDTO(CompileStatusEnum.SUCCESS.getCode(), "通过全部用例");
    }
}
