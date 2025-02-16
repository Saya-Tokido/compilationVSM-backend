package com.ljz.compilationVSM.domain.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljz.compilationVSM.common.config.cache.HashSetCache;
import com.ljz.compilationVSM.common.constant.Constants;
import com.ljz.compilationVSM.common.utils.*;
import com.ljz.compilationVSM.common.enums.RoleEnum;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.domain.account.dto.StudentUserCreateRequestDTO;
import com.ljz.compilationVSM.domain.account.dto.TeacherUserCreateRequestDTO;
import com.ljz.compilationVSM.domain.account.service.AccountManagerService;
import com.ljz.compilationVSM.domain.convert.AccountManagerMapping;
import com.ljz.compilationVSM.infrastructure.mapper.UserMapper;
import com.ljz.compilationVSM.infrastructure.po.StudentPO;
import com.ljz.compilationVSM.infrastructure.po.TeacherPO;
import com.ljz.compilationVSM.infrastructure.po.UserPO;
import com.ljz.compilationVSM.infrastructure.repository.StudentRepository;
import com.ljz.compilationVSM.infrastructure.repository.TeacherRepository;
import com.ljz.compilationVSM.infrastructure.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 账号管理服务实现类
 *
 * @author ljz
 * @since 2025-01-20
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountManagerServiceImpl implements AccountManagerService {

    private final UserRepository userRepository;
    private final BloomFilterUtil bloomFilterUtil;
    private final SnowflakeIdGenerator idGenerator;
    private final AccountManagerMapping accountManagerMapping;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final HashSetCache hashSetCache;
    private final UserMapper userMapper;
    private final MessageEncodeUtil md5EncodeUtil;
    private final BCryptUtil bCryptUtil;

    @Value("${account.password.student}")
    private String stuPassword;

    @Value("${account.password.teacher}")
    private String teachPassword;

    @Value("${cache-key.exist-user}")
    private String existUserKey;

    @Value("${cache-key.absent-user}")
    private String absentUserKey;

    /**
     * 教学班列表分隔符
     */
    @Value("${info.teach-class.delimiter}")
    private String teachClassDelimiter;

    @PostConstruct
    public void init() {
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.<UserPO>lambdaQuery()
                .select(UserPO::getUserName)
                .eq(UserPO::getIsDelete, Boolean.FALSE);
        List<UserPO> userPOList = userRepository.list(queryWrapper);
        if (!CollectionUtils.isEmpty(userPOList)) {
            List<String> userNameList = userPOList.stream().map(UserPO::getUserName).toList();
            bloomFilterUtil.warmUp(userNameList);
        }
    }

    @Override
    @Transactional
    public void addStudentUser(StudentUserCreateRequestDTO requestDTO) {
        // 用户名为学号，查询该用户是否被创建过
        // 先查询存在用户缓存
        Set<String> existUser = hashSetCache.getCachedSet(existUserKey);
        if (existUser.contains(requestDTO.getNumber())) {
            log.warn("新建学生用户,用户名已存在,userName = {}", requestDTO.getNumber());
            throw new BizException(BizExceptionCodeEnum.USER_NAME_EXISTED_ERROR);
        }
        // 查询布隆过滤器
        boolean exists = bloomFilterUtil.exists(requestDTO.getNumber());
        if (!exists) {
            createStudentUser(requestDTO);
        } else {
            // 布隆过滤器可能存在误判，查询不存在用户缓存
            Set<String> absentUser = hashSetCache.getCachedSet(absentUserKey);
            if (absentUser.contains(requestDTO.getNumber())) {
                createStudentUser(requestDTO);
            } else {
                // 查询数据库，做误判兜底
                UserPO queryDTO = new UserPO();
                queryDTO.setUserName(requestDTO.getNumber());
                UserPO user = userMapper.getUser(queryDTO);
                if (Objects.nonNull(user)) {
                    hashSetCache.addElement(existUserKey, requestDTO.getNumber(), Constants.SIXTY);
                    log.warn("新建学生用户,用户名已存在,userName = {}", requestDTO.getNumber());
                    throw new BizException(BizExceptionCodeEnum.USER_NAME_EXISTED_ERROR);
                } else {
                    hashSetCache.addElement(absentUserKey, requestDTO.getNumber(), Constants.SIXTY);
                    createStudentUser(requestDTO);
                    hashSetCache.addElement(existUserKey, requestDTO.getNumber(), Constants.SIXTY);
                }
            }
        }
    }

    @Override
    public void addTeacherUser(TeacherUserCreateRequestDTO requestDTO) {
        // 用户名为工号，查询该用户是否被创建过
        // 先查询存在用户缓存
        Set<String> existUser = hashSetCache.getCachedSet(existUserKey);
        if (existUser.contains(requestDTO.getNumber())) {
            log.warn("新建教师用户,用户名已存在,userName = {}", requestDTO.getNumber());
            throw new BizException(BizExceptionCodeEnum.USER_NAME_EXISTED_ERROR);
        }
        // 查询布隆过滤器
        boolean exists = bloomFilterUtil.exists(requestDTO.getNumber());
        if (!exists) {
            createTeacherUser(requestDTO);
        } else {
            // 布隆过滤器可能存在误判，查询不存在用户缓存
            Set<String> absentUser = hashSetCache.getCachedSet(absentUserKey);
            if (absentUser.contains(requestDTO.getNumber())) {
                createTeacherUser(requestDTO);
            } else {
                // 查询数据库，做误判兜底
                UserPO queryDTO = new UserPO();
                queryDTO.setUserName(requestDTO.getNumber());
                UserPO user = userMapper.getUser(queryDTO);
                if (Objects.nonNull(user)) {
                    hashSetCache.addElement(existUserKey, requestDTO.getNumber(), Constants.SIXTY);
                    log.warn("新建教师用户,用户名已存在,userName = {}", requestDTO.getNumber());
                    throw new BizException(BizExceptionCodeEnum.USER_NAME_EXISTED_ERROR);
                } else {
                    hashSetCache.addElement(absentUserKey, requestDTO.getNumber(), Constants.SIXTY);
                    createTeacherUser(requestDTO);
                    hashSetCache.addElement(existUserKey, requestDTO.getNumber(), Constants.SIXTY);
                }
            }
        }
    }

    @Override
    @Transactional
    public void addStudentUserBatch(List<StudentUserCreateRequestDTO> requestDTO) {
        requestDTO.forEach(this::addStudentUser);
    }

    @Override
    public void deleteStudentAccount(String number) {
        // todo
        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.<UserPO>lambdaQuery()
                .select(UserPO::getId)
                .eq(UserPO::getIsDelete, Boolean.FALSE)
                .eq(UserPO::getUserName,number);
        UserPO userPO = userRepository.getOne(queryWrapper);
        if(Objects.isNull(userPO)){
//            log.warn("");
            throw new BizException(BizExceptionCodeEnum.USER_NOT_EXIST_ERROR);
        }
    }

    @Override
    public void deleteTeacherAccount(String number) {
        // todo
    }

    /**
     * create user and student
     *
     * @param requestDTO request param
     */
    private void createStudentUser(StudentUserCreateRequestDTO requestDTO) {
        // 创建用户
        UserPO userPO = new UserPO();
        Long userId = idGenerator.generate();
        userPO.setId(userId);
        userPO.setRole(Integer.parseInt(RoleEnum.STUDENT.getCode()));
        userPO.setUserName(requestDTO.getNumber());
        String encryptedPassword = bCryptUtil.encryptMessage(md5EncodeUtil.encode(stuPassword));
        userPO.setPassword(encryptedPassword);
        userRepository.save(userPO);
        // 学生信息入库
        StudentPO studentPO = accountManagerMapping.convert(requestDTO);
        studentPO.setId(idGenerator.generate());
        studentPO.setUserId(userId);
        studentRepository.save(studentPO);
        bloomFilterUtil.add(requestDTO.getNumber());
    }

    /**
     * create user and teacher
     *
     * @param requestDTO request param
     */
    private void createTeacherUser(TeacherUserCreateRequestDTO requestDTO) {
        // 创建用户
        UserPO userPO = new UserPO();
        Long userId = idGenerator.generate();
        userPO.setId(userId);
        userPO.setRole(Integer.parseInt(RoleEnum.TEACHER.getCode()));
        userPO.setUserName(requestDTO.getNumber());
        String encryptedPassword = bCryptUtil.encryptMessage(md5EncodeUtil.encode(teachPassword));
        userPO.setPassword(encryptedPassword);
        userRepository.save(userPO);
        // 教师信息入库
        TeacherPO teacherPO = new TeacherPO();
        teacherPO.setId(idGenerator.generate());
        teacherPO.setUserId(userId);
        teacherPO.setName(requestDTO.getName());
        teacherPO.setNumber(Long.parseLong(requestDTO.getNumber()));
        String teachClassListStr = requestDTO.getTeachClass().stream()
                .map(str -> str.replaceAll(teachClassDelimiter, ""))
                .collect(Collectors.joining(teachClassDelimiter));
        teacherPO.setClassList(teachClassListStr);
        teacherRepository.save(teacherPO);
        bloomFilterUtil.add(requestDTO.getNumber());
    }
}
