package com.ljz.compilationVSM.service.impl;

import com.ljz.compilationVSM.dto.CodeDto;
import com.ljz.compilationVSM.service.ExperimentService;
import com.ljz.compilationVSM.util.Compiler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ExperimentServiceImpl implements ExperimentService {

    @Override
    public String checkExperiment(CodeDto codeDto){
        HashMap<String,String> resultMap= Compiler.remoteCompiler(codeDto);
        if(resultMap.get("output").equals("")){
            return resultMap.get("errors");
        }
        else{
            //check the output
            String standard="1 基本字 const";
            String output=resultMap.get("output");
            if(output.equals(standard)){
                return "success";
            }
            else{
                return "error output :\n"+output;
            }
        }
    }
}
