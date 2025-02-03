package com.ljz.compilationVSM.api.request.teacher;

import lombok.Getter;
import lombok.Setter;

/**
 * 客观题教师调分请求
 *
 * @author ljz
 * @since 2025-02-03
 */
@Getter
@Setter
public class ObjScoreModifyRequest {

    /**
     * 学生学号
     */
    String number;

    /**
     * 调整后的分数
     */
    Integer score;

}
