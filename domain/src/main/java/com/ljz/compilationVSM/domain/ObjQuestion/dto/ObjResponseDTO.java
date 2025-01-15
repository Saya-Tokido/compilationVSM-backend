package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 获取客观题响应
 *
 * @author ljz
 * @since 2025-01-13
 */
@Getter
@Setter
public class ObjResponseDTO {

    /**
     * 是否是练习（已打分过）
     */
    Integer practise;

    /**
     * 选择题题目列表
     */
    List<ChooseDTO> chooseList;

    /**
     * 填空题题目列表
     */
    List<FillDTO> fillList;
}
