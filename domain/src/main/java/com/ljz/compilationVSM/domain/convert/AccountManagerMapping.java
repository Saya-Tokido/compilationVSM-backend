package com.ljz.compilationVSM.domain.convert;

import com.ljz.compilationVSM.domain.account.dto.StudentUserCreateRequestDTO;
import com.ljz.compilationVSM.infrastructure.po.StudentPO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountManagerMapping {
    StudentPO convert(StudentUserCreateRequestDTO source);
}
