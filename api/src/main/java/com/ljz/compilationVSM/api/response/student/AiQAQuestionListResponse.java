package com.ljz.compilationVSM.api.response.student;

import com.ljz.compilationVSM.common.dto.base.KeyValueDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AiQAQuestionListResponse{
    private List<KeyValueDTO<String,String>> questionList;
}
