package com.ljz.compilationVSM.web.convert.student;

import com.ljz.compilationVSM.api.response.common.SelfInfoResponse;
import com.ljz.compilationVSM.api.response.student.StudentSelfInfoResponse;
import com.ljz.compilationVSM.domain.info.dto.SelfInfoResponseDTO;
import com.ljz.compilationVSM.domain.info.dto.StudentSelfInfoResponseDTO;
import org.mapstruct.Mapper;

/**
 * 学生用户基本信息对象映射
 *
 * @author ljz
 * @since 2025-02-07
 */
@Mapper(componentModel = "Spring")
public interface StudentSelfInfoMapping {
    StudentSelfInfoResponse convert(StudentSelfInfoResponseDTO source);

    SelfInfoResponse convert(SelfInfoResponseDTO source);
}
