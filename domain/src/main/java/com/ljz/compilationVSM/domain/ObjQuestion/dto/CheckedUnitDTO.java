package com.ljz.compilationVSM.domain.ObjQuestion.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 客观题校验后的反馈片段
 *
 * @author ljz
 * @since 2024-12-28
 */
@Getter
@Setter
public class CheckedUnitDTO {

    /**
     * 正确答案
     */
    String answer;

    /**
     * 是否正确
     */
    Boolean mark;
}
