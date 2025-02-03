package com.ljz.compilationVSM.web.convert.teacher;

import com.ljz.compilationVSM.api.request.teacher.StudentPageQueryRequest;
import com.ljz.compilationVSM.api.response.teacher.StudentInfoPageResponse;
import com.ljz.compilationVSM.domain.info.dto.StudentInfoPageResponseDTO;
import com.ljz.compilationVSM.domain.info.dto.StudentPageQueryRequestDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 学生信息管理对象映射
 *
 * @author ljz
 * @since 2025-01-28
 */
@Mapper(componentModel = "spring",imports = {List.class})
public interface StudentManagerMapping {
    StudentPageQueryRequestDTO convert(StudentPageQueryRequest source);

    StudentInfoPageResponse convert(StudentInfoPageResponseDTO source);

}
