package com.ljz.compilationVSM.domain.info.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljz.compilationVSM.common.constant.Constants;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.common.utils.UserContextHolder;
import com.ljz.compilationVSM.domain.convert.BasicInfoManagerMapping;
import com.ljz.compilationVSM.domain.info.dto.StudentInfoPageResponseDTO;
import com.ljz.compilationVSM.domain.info.dto.StudentPageQueryRequestDTO;
import com.ljz.compilationVSM.domain.info.service.BasicInfoManagerService;
import com.ljz.compilationVSM.infrastructure.mapper.StudentMapper;
import com.ljz.compilationVSM.infrastructure.po.StudentPO;
import com.ljz.compilationVSM.infrastructure.po.TeacherPO;
import com.ljz.compilationVSM.infrastructure.repository.StudentRepository;
import com.ljz.compilationVSM.infrastructure.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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
    private final StudentMapper studentMapper;

    /**
     * 教学班列表分隔符
     */
    @Value("${info.teach-class.delimiter}")
    private String teachClassDelimiter;

    @Override
    public StudentInfoPageResponseDTO pageQueryStudentInfo(StudentPageQueryRequestDTO requestDTO) {
        // 获取教师的所带教学班
        Long userId = UserContextHolder.getUserId();
        LambdaQueryWrapper<TeacherPO> teacherQueryWrapper = Wrappers.<TeacherPO>lambdaQuery()
                .select(TeacherPO::getClassList)
                .eq(TeacherPO::getIsDelete, Boolean.FALSE)
                .eq(TeacherPO::getUserId, userId);
        String[] classList = teacherRepository.getOne(teacherQueryWrapper).getClassList().split(teachClassDelimiter, -1);
        if (0 == classList.length) {
            return null;
        }
        // 构造查询参数
        LambdaQueryWrapper<StudentPO> studentQueryWrapper;
        if (CollectionUtils.isEmpty(requestDTO.getClassList())) {
            studentQueryWrapper = buildBasicInfoQuery(requestDTO, Arrays.stream(classList).toList());
        } else {
            Set<String> queryClassSet = new HashSet<>(requestDTO.getClassList());
            if (Arrays.stream(classList).collect(Collectors.toSet()).containsAll(queryClassSet)) {
                studentQueryWrapper = buildBasicInfoQuery(requestDTO, queryClassSet);
            } else {
                log.error("教师用户查询学生信息,查询条件包含非所带教学班,queryClassList = {}, teacher's classList = {}", queryClassSet, Arrays.toString(classList));
                throw new BizException(BizExceptionCodeEnum.PARAMETER_ERROR, "无法访问本所带教学班");
            }
        }
        if (Objects.nonNull(requestDTO.getObjAsc())) {
            studentQueryWrapper.orderByAsc(Constants.ONE.equals(requestDTO.getObjAsc()), StudentPO::getObjScore);
            studentQueryWrapper.orderByDesc(Constants.ZERO.equals(requestDTO.getObjAsc()), StudentPO::getObjScore);
        }
        if (Objects.nonNull(requestDTO.getLexerAsc())) {
            studentQueryWrapper.orderByAsc(Constants.ONE.equals(requestDTO.getLexerAsc()), StudentPO::getLexerScore);
            studentQueryWrapper.orderByDesc(Constants.ZERO.equals(requestDTO.getLexerAsc()), StudentPO::getLexerScore);
        }
        studentQueryWrapper.orderByAsc(StudentPO::getId);

        Page<StudentPO> queryPage = new Page<>();
        queryPage.setCurrent(requestDTO.getPageIndex());
        queryPage.setSize(requestDTO.getPageSize());
        Page<StudentPO> studentInfoPage = studentRepository.page(queryPage, studentQueryWrapper);
        StudentInfoPageResponseDTO responseDTO = basicInfoManagerMapping.convert(studentInfoPage);
        responseDTO.setClassList(Arrays.stream(classList).toList());
        responseDTO.setTotalPages((int) studentInfoPage.getPages());
        return responseDTO;
    }

    @Override
    public StudentInfoPageResponseDTO pageQueryStudentInfoByAdmin(StudentPageQueryRequestDTO requestDTO) {
        LambdaQueryWrapper<StudentPO> queryWrapper;
        if (!CollectionUtils.isEmpty(requestDTO.getClassList())) {
            queryWrapper = buildBasicInfoQuery(requestDTO, requestDTO.getClassList());
        } else {
            queryWrapper = buildBasicInfoQuery(requestDTO, null);
        }
        queryWrapper.orderByAsc(StudentPO::getId);
        Page<StudentPO> queryPage = new Page<>();
        queryPage.setCurrent(requestDTO.getPageIndex());
        queryPage.setSize(requestDTO.getPageSize());
        Page<StudentPO> studentInfoPage = studentRepository.page(queryPage, queryWrapper);
        StudentInfoPageResponseDTO responseDTO = basicInfoManagerMapping.convert(studentInfoPage);
        // 查询所有教学班
        List<String> classList = studentMapper.getAllAdminClass();
        responseDTO.setClassList(classList);
        responseDTO.setTotalPages((int) studentInfoPage.getPages());
        return responseDTO;
    }

    /**
     * 学生信息查询条件构造
     *
     * @param requestDTO 请求参数
     * @param classList  需要查询的教学班列表，如果为null则查询所有教学班
     * @return 查询参数包装器
     */
    private LambdaQueryWrapper<StudentPO> buildBasicInfoQuery(StudentPageQueryRequestDTO requestDTO, Collection<String> classList) {
        LambdaQueryWrapper<StudentPO> studentQueryWrapper = Wrappers.<StudentPO>lambdaQuery()
                .select(StudentPO::getNumber, StudentPO::getName, StudentPO::getAdminClass, StudentPO::getTeachClass, StudentPO::getObjScore, StudentPO::getLexerScore)
                .eq(StudentPO::getIsDelete, Boolean.FALSE);
        if (StringUtils.isNotBlank(requestDTO.getNumber())) {
            studentQueryWrapper.eq(StudentPO::getNumber, Long.parseLong(requestDTO.getNumber()));
        }
        if (StringUtils.isNotBlank(requestDTO.getStuName())) {
            studentQueryWrapper.likeRight(StudentPO::getName, requestDTO.getStuName());
        }
        if (!CollectionUtils.isEmpty(classList)) {
            studentQueryWrapper.in(StudentPO::getTeachClass, classList);
        } else if (Objects.nonNull(classList)) {
            log.warn("分页查询学生信息,无权查看任意教学班,userId = {}", UserContextHolder.getUserId());
            throw new BizException(BizExceptionCodeEnum.NO_CLASS_ACCESS_ERROR);
        }
        return studentQueryWrapper;
    }
}
