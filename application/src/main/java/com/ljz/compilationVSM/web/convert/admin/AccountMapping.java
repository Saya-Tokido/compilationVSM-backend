package com.ljz.compilationVSM.web.convert.admin;

import com.ljz.compilationVSM.api.request.admin.StudentUserCreateRequest;
import com.ljz.compilationVSM.api.request.admin.TeacherUserCreateRequest;
import com.ljz.compilationVSM.domain.account.dto.StudentUserCreateRequestDTO;
import com.ljz.compilationVSM.domain.account.dto.TeacherUserCreateRequestDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 账户管理接口参数映射
 *
 * @author ljz
 * @since 2025-01-20
 */
@Mapper(componentModel = "spring",imports = {List.class})
public interface AccountMapping {
    StudentUserCreateRequestDTO convert(StudentUserCreateRequest source);

    TeacherUserCreateRequestDTO convert(TeacherUserCreateRequest source);
}
