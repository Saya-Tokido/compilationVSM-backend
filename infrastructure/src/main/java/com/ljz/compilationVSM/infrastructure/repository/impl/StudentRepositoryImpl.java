package com.ljz.compilationVSM.infrastructure.repository.impl;

import com.ljz.compilationVSM.infrastructure.po.StudentPO;
import com.ljz.compilationVSM.infrastructure.mapper.StudentMapper;
import com.ljz.compilationVSM.infrastructure.repository.StudentRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author ljz
 * @since 2024-12-25 10:06:38
 */
@Service
public class StudentRepositoryImpl extends ServiceImpl<StudentMapper, StudentPO> implements StudentRepository {

}
