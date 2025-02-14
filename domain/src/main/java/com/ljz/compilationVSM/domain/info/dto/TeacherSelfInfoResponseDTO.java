package com.ljz.compilationVSM.domain.info.dto;

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
public class TeacherSelfInfoResponseDTO {

    /**
     * 基本信息
     */
    SelfInfoResponseDTO basicInfo;

    /**
     * 所带教学班
     */
    List<String>  classList;

}
