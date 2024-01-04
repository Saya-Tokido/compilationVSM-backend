package com.ljz.compilationVSM.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljz.compilationVSM.entity.Choose;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChooseMapper extends BaseMapper<Choose> {
    public List<Choose> getQuestion(int number);
}
