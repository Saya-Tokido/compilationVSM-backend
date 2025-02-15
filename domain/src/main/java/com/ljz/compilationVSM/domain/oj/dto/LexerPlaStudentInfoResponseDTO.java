package com.ljz.compilationVSM.domain.oj.dto;

import com.ljz.compilationVSM.domain.ObjQuestion.dto.StudentBaseInfoResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 词法分析器题代码抄袭学生信息DTO
 *
 * @author ljz
 * @since 2025-02-15
 */
@Getter
@Setter
public class LexerPlaStudentInfoResponseDTO {

    /**
     * 抄袭学生列表
     */
    List<StudentBaseInfoResponseDTO> studentList;
}
