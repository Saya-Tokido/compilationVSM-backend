package com.ljz.compilationVSM.domain.experiment.service.impl;

import com.ljz.compilationVSM.dependency.dto.CompilationOutputDTO;
import com.ljz.compilationVSM.dependency.facade.RemoteCompilerFacade;
import com.ljz.compilationVSM.domain.convert.CompilationMapping;
import com.ljz.compilationVSM.domain.experiment.dto.CodeDTO;
import com.ljz.compilationVSM.domain.experiment.dto.FeedbackDTO;
import com.ljz.compilationVSM.domain.experiment.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExperimentServiceImpl implements ExperimentService {

    private RemoteCompilerFacade remoteCompilerFacade;
    private CompilationMapping compilationMapping;
    @Autowired
    public ExperimentServiceImpl(RemoteCompilerFacade remoteCompilerFacade,CompilationMapping compilationMapping){
        this.remoteCompilerFacade=remoteCompilerFacade;
        this.compilationMapping=compilationMapping;
    }

    @Override
    public FeedbackDTO checkExperiment(CodeDTO codeDTO){


        CompilationOutputDTO outputDTO = remoteCompilerFacade.compile(compilationMapping.convert(codeDTO));
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setOutput(outputDTO.getOutput());
        feedbackDTO.setPassed(false);
        //todo 编译出错处理
        if(outputDTO.getCompilationError()){
            feedbackDTO.setErrorMessage("编译出错");
        }else{
            checkAnswer(feedbackDTO,outputDTO);
        }
        return feedbackDTO;
    }

    /**
     * 设置评判逻辑
     * @return
     */
    private void checkAnswer(FeedbackDTO feedbackDTO,CompilationOutputDTO outputDTO){
        //todo 评判逻辑待设置
        feedbackDTO.setErrorMessage("答案错误");
    }
}
