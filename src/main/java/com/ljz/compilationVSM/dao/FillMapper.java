package com.ljz.compilationVSM.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljz.compilationVSM.entity.Fill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FillMapper extends BaseMapper<Fill> {
    List<Fill> getQuestion(int number);
}
