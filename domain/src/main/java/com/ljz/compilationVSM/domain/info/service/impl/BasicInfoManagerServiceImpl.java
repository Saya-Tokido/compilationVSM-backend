package com.ljz.compilationVSM.domain.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljz.compilationVSM.common.constant.Constant;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.common.utils.UserContextHolder;
import com.ljz.compilationVSM.domain.convert.BasicInfoManagerMapping;
import com.ljz.compilationVSM.domain.info.dto.StudentInfoPageResponseDTO;
import com.ljz.compilationVSM.domain.info.dto.StudentPageQueryRequestDTO;
import com.ljz.compilationVSM.domain.info.service.BasicInfoManagerService;
import com.ljz.compilationVSM.infrastructure.po.StudentPO;
import com.ljz.compilationVSM.infrastructure.po.TeacherPO;
import com.ljz.compilationVSM.infrastructure.repository.StudentRepository;
import com.ljz.compilationVSM.infrastructure.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 基本信息管理服务实现类
 *
 * @author ljz
 * @since 2025-01-27
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BasicInfoManagerServiceImpl implements BasicInfoManagerService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final BasicInfoManagerMapping basicInfoManagerMapping;

    @Override
    public StudentInfoPageResponseDTO pageQueryStudentInfo(StudentPageQueryRequestDTO requestDTO) {
        // 获取教师的所带教学班
        Long userId = UserContextHolder.getUserId();
        LambdaQueryWrapper<TeacherPO> teacherQueryWrapper = Wrappers.<TeacherPO>lambdaQuery()
                .select(TeacherPO::getClassList)
                .eq(TeacherPO::getIsDelete, Boolean.FALSE)
                .eq(TeacherPO::getUserId, userId);
        String[] classList = teacherRepository.getOne(teacherQueryWrapper).getClassList().split("\\s");
        if (0 == classList.length) {
            return null;
        }
        // 构造查询参数
        LambdaQueryWrapper<StudentPO> studentQueryWrapper = Wrappers.<StudentPO>lambdaQuery()
                .select(StudentPO::getNumber, StudentPO::getName, StudentPO::getAdminClass, StudentPO::getTeachClass, StudentPO::getObjGrade, StudentPO::getLexerGrade)
                .eq(StudentPO::getIsDelete, Boolean.FALSE);
        if (StringUtils.isNotBlank(requestDTO.getNumber())) {
            studentQueryWrapper.eq(StudentPO::getNumber, Long.parseLong(requestDTO.getNumber()));
        }
        if (StringUtils.isNotBlank(requestDTO.getStuName())) {
            studentQueryWrapper.likeRight(StudentPO::getName, requestDTO.getStuName());
        }
        if (CollectionUtils.isEmpty(requestDTO.getClassList())) {
            studentQueryWrapper.in(StudentPO::getTeachClass, Arrays.stream(classList).toList());
        } else {
            Set<String> queryClassSet = new HashSet<>(requestDTO.getClassList());
            if (Arrays.stream(classList).collect(Collectors.toSet()).containsAll(queryClassSet)) {
                studentQueryWrapper.in(StudentPO::getTeachClass, queryClassSet);
            } else {
                log.error("教师用户查询学生信息,查询条件包含非所带教学班,queryClassList = {}, teacher's classList = {}", queryClassSet, Arrays.toString(classList));
                throw new BizException(BizExceptionCodeEnum.PARAMETER_ERROR);
            }
        }
        if (Objects.nonNull(requestDTO.getObjAsc())) {
            studentQueryWrapper.orderByAsc(Constant.ONE.equals(requestDTO.getObjAsc()), StudentPO::getObjGrade);
            studentQueryWrapper.orderByDesc(Constant.ZERO.equals(requestDTO.getObjAsc()), StudentPO::getObjGrade);
        }
        if (Objects.nonNull(requestDTO.getLexerAsc())) {
            studentQueryWrapper.orderByAsc(Constant.ONE.equals(requestDTO.getLexerAsc()), StudentPO::getLexerGrade);
            studentQueryWrapper.orderByDesc(Constant.ZERO.equals(requestDTO.getLexerAsc()), StudentPO::getLexerGrade);
        }
        studentQueryWrapper.orderByAsc(StudentPO::getId);
        Page<StudentPO> queryPage = new Page<>();
        queryPage.setCurrent(requestDTO.getPageIndex());
        queryPage.setSize(requestDTO.getPageSize());
        Page<StudentPO> studentInfoPage = studentRepository.page(queryPage,studentQueryWrapper);
        StudentInfoPageResponseDTO responseDTO = basicInfoManagerMapping.convert(studentInfoPage);
        responseDTO.setClassList(Arrays.stream(classList).toList());
        responseDTO.setTotalPages((int) studentInfoPage.getPages());
        return responseDTO;
    }
}
