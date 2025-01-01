package com.ljz.compilationVSM.api.response.student;

import com.ljz.compilationVSM.api.response.student.ChooseResponse;
import com.ljz.compilationVSM.api.response.student.FillResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 获取客观题响应
 *
 * @author ljz
 * @since 2024-12-28
 */
@Getter
@Setter
public class ObjResponse {

    /**
     * 选择题题目列表
     */
    List<ChooseResponse> chooseList;

    /**
     * 填空题题目列表
     */
    List<FillResponse> fillList;
}
