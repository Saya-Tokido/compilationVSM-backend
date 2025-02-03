package com.ljz.compilationVSM.domain.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljz.compilationVSM.domain.info.dto.StudentInfoPageResponseDTO;
import com.ljz.compilationVSM.infrastructure.po.StudentPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * basic info manager mapping
 *
 * @author ljz
 * @since 2025-01-29
 */
@Mapper(componentModel = "spring", imports = {List.class})
public interface BasicInfoManagerMapping {

    @Mappings({
            @Mapping(target = "studentInfoList", source = "records"),
            @Mapping(target = "currentPage", source = "current"),
            @Mapping(target = "totalRecords", source = "total")
    })
    StudentInfoPageResponseDTO convert(Page<StudentPO> source);

    StudentInfoPageResponseDTO.StudentInfo convert(StudentPO source);
}
