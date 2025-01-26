package com.ljz.compilationVSM.web.convert;

import com.ljz.compilationVSM.api.request.admin.StudentUserCreateRequest;
import com.ljz.compilationVSM.domain.account.dto.StudentUserCreateRequestDTO;
import org.mapstruct.Mapper;

/**
 * 账户管理接口参数映射
 *
 * @author ljz
 * @since 2025-01-20
 */
@Mapper(componentModel = "spring")
public interface AccountMapping {
    StudentUserCreateRequestDTO convert(StudentUserCreateRequest source);
}
