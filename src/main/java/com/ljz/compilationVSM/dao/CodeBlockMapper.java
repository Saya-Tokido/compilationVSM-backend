package com.ljz.compilationVSM.dao;

import com.ljz.compilationVSM.entity.MethodBody;
import com.ljz.compilationVSM.entity.MethodName;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeBlockMapper {
    public List<String> getComment(String language, String compLanguage, String method);
    public List<MethodName> getMethodName(String language, String compLanguage);
    public MethodBody getMethodBody(Integer methodId);

}