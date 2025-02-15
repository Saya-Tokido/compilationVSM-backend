package com.ljz.compilationVSM.infrastructure.mapper;

import com.ljz.compilationVSM.infrastructure.po.StudentPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author ljz
 * @since 2024-12-25 09:58:21
 */
public interface StudentMapper extends BaseMapper<StudentPO> {
    void updateStudentInfo(StudentPO studentPO);

    List<StudentPO> getStudentsByCodeIds(Set<Long> ids);
}
