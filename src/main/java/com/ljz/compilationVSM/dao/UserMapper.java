package com.ljz.compilationVSM.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljz.compilationVSM.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
