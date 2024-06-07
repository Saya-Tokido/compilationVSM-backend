package com.ljz.compilationVSM.commonTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljz.compilationVSM.BaseTest;
import com.ljz.compilationVSM.common.Result;
import com.ljz.compilationVSM.common.dto.base.KeyValueDTO;
import com.ljz.compilationVSM.entity.AiQAPO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CommonTest extends BaseTest {

    @Test
    public void toJsonString(){
        Result error = Result.error(HttpStatus.UNAUTHORIZED.value(), "Authentication failed,please log in again!");
        ObjectMapper mapper = new ObjectMapper();
        try{
            String jsonString = mapper.writeValueAsString(error);
        }catch(Exception e){
            log.error("error",e);
        }
        new ArrayList<AiQAPO>().stream().map(
                item->new KeyValueDTO<>(
                        item.getId().toString(),
                        item.getQuestion()
                )
        ).collect(Collectors.toList());
    }
    @Test
    public void myTest(){
        List<AiQAPO> aiQAPOS = new ArrayList<>();
        AiQAPO aiQAPO =new AiQAPO();
        aiQAPO.setId(3424L);
        aiQAPO.setQuestion("sfsfgsd");
        aiQAPOS.add(aiQAPO);
        System.out.println(aiQAPOS.stream().map(item->new KeyValueDTO<>(item.getId().toString(),item.getQuestion())).collect(Collectors.toList()));
        System.out.println("sdfsd");
    }
}
