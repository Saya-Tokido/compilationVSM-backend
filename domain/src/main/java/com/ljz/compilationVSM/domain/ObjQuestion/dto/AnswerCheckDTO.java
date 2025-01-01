package com.ljz.compilationVSM.domain.ObjQuestion.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * 客观题校验DTO
 *
 * @author ljz
 * @since 2024-12-28
 */
@Getter
@Setter
public class AnswerCheckDTO {

    /**
     * 题号id
     */
    private Long id;

    /**
     * 作答内容（选择题传入的是选项，填空题是内容）
     */
    private String answer;
}
