package com.ljz.compilationVSM.api.response.teacher;

import com.ljz.compilationVSM.api.response.common.SelfInfoResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 教师用户基本信息响应
 *
 * @author ljz
 * @since 2025-02-07
 */
@Getter
@Setter
public class TeacherSelfInfoResponse {

    /**
     * 基本信息
     */
    SelfInfoResponse basicInfo;

    /**
     * 所带教学班
     */
    List<String>  classList;

}
