package com.ljz.compilationVSM.web.convert.admin;

import com.ljz.compilationVSM.api.request.admin.StudentPageQueryRequest;
import com.ljz.compilationVSM.api.request.admin.StudentUserCreateRequest;
import com.ljz.compilationVSM.api.request.admin.TeacherUserCreateRequest;
import com.ljz.compilationVSM.api.response.admin.StudentInfoPageResponse;
import com.ljz.compilationVSM.domain.account.dto.StudentUserCreateRequestDTO;
import com.ljz.compilationVSM.domain.account.dto.TeacherUserCreateRequestDTO;
import com.ljz.compilationVSM.domain.info.dto.StudentInfoPageResponseDTO;
import com.ljz.compilationVSM.domain.info.dto.StudentPageQueryRequestDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 账户管理接口参数映射
 *
 * @author ljz
 * @since 2025-01-20
 */
@Mapper(componentModel = "spring", imports = {List.class})
public interface AccountMapping {
    StudentUserCreateRequestDTO convert(StudentUserCreateRequest source);

    TeacherUserCreateRequestDTO convert(TeacherUserCreateRequest source);

    List<StudentUserCreateRequestDTO> convert(List<StudentUserCreateRequest> source);

    StudentPageQueryRequestDTO convert(StudentPageQueryRequest source);

    StudentInfoPageResponse convert(StudentInfoPageResponseDTO source);

    StudentInfoPageResponse.StudentInfo convert(StudentInfoPageResponseDTO.StudentInfo source);
}
