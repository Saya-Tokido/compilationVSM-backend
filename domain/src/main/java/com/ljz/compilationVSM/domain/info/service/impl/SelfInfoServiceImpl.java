package com.ljz.compilationVSM.domain.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljz.compilationVSM.common.utils.UserContextHolder;
import com.ljz.compilationVSM.domain.info.dto.SelfInfoResponseDTO;
import com.ljz.compilationVSM.domain.info.dto.StudentSelfInfoResponseDTO;
import com.ljz.compilationVSM.domain.info.dto.TeacherSelfInfoResponseDTO;
import com.ljz.compilationVSM.domain.info.service.SelfInfoService;
import com.ljz.compilationVSM.infrastructure.po.StudentPO;
import com.ljz.compilationVSM.infrastructure.po.TeacherPO;
import com.ljz.compilationVSM.infrastructure.repository.StudentRepository;
import com.ljz.compilationVSM.infrastructure.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 用户基本信息服务实现
 *
 * @author ljz
 * @since 2025-02-07
 */
@Service
@RequiredArgsConstructor
public class SelfInfoServiceImpl implements SelfInfoService {

    private final TeacherRepository teacherRepository;

    private final StudentRepository studentRepository;

    /**
     * 教学班列表分隔符
     */
    @Value("${info.teach-class.delimiter}")
    private String teachClassDelimiter;

    @Override
    public TeacherSelfInfoResponseDTO getTeacherSelfInfo() {
        Long userId = UserContextHolder.getUserId();
        LambdaQueryWrapper<TeacherPO> queryWrapper = Wrappers.<TeacherPO>lambdaQuery()
                .eq(TeacherPO::getIsDelete, Boolean.FALSE)
                .eq(TeacherPO::getUserId, userId);
        TeacherPO teacherPO = teacherRepository.getOne(queryWrapper);
        TeacherSelfInfoResponseDTO responseDTO = new TeacherSelfInfoResponseDTO();
        SelfInfoResponseDTO selfInfoResponseDTO = new SelfInfoResponseDTO();
        selfInfoResponseDTO.setName(teacherPO.getName());
        selfInfoResponseDTO.setNumber(teacherPO.getNumber().toString());
        responseDTO.setBasicInfo(selfInfoResponseDTO);
        responseDTO.setClassList(Arrays.asList(teacherPO.getClassList().split(teachClassDelimiter, -1)));
        return responseDTO;
    }

    @Override
    public StudentSelfInfoResponseDTO getStudentSelfInfo() {
        Long userId = UserContextHolder.getUserId();
        LambdaQueryWrapper<StudentPO> queryWrapper = Wrappers.<StudentPO>lambdaQuery()
                .eq(StudentPO::getIsDelete, Boolean.FALSE)
                .eq(StudentPO::getUserId, userId);
        StudentPO studentPO = studentRepository.getOne(queryWrapper);
        StudentSelfInfoResponseDTO responseDTO = new StudentSelfInfoResponseDTO();
        SelfInfoResponseDTO selfInfoResponseDTO = new SelfInfoResponseDTO();
        selfInfoResponseDTO.setName(studentPO.getName());
        selfInfoResponseDTO.setNumber(studentPO.getNumber().toString());
        responseDTO.setBasicInfo(selfInfoResponseDTO);
        responseDTO.setAdminClass(studentPO.getAdminClass());
        responseDTO.setTeachClass(studentPO.getTeachClass());
        responseDTO.setObjScore(studentPO.getObjScore());
        responseDTO.setLexerScore(studentPO.getLexerScore());
        return responseDTO;
    }
}
