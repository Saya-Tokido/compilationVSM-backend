package com.ljz.compilationVSM.infrastructure.repository.impl;

import com.ljz.compilationVSM.infrastructure.po.TeacherPO;
import com.ljz.compilationVSM.infrastructure.mapper.TeacherMapper;
import com.ljz.compilationVSM.infrastructure.repository.TeacherRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 教师表 服务实现类
 * </p>
 *
 * @author ljz
 * @since 2024-12-25 10:06:38
 */
@Service
public class TeacherRepositoryImpl extends ServiceImpl<TeacherMapper, TeacherPO> implements TeacherRepository {

}
