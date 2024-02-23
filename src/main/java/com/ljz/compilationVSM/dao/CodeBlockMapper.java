package com.ljz.compilationVSM.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeBlockMapper {
    public List<String> getComment(String language, String compLanguage, String method);
}