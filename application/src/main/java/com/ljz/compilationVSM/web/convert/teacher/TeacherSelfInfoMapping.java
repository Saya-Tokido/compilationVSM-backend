package com.ljz.compilationVSM.web.convert.teacher;

import com.ljz.compilationVSM.api.response.common.SelfInfoResponse;
import com.ljz.compilationVSM.api.response.teacher.TeacherSelfInfoResponse;
import com.ljz.compilationVSM.domain.info.dto.SelfInfoResponseDTO;
import com.ljz.compilationVSM.domain.info.dto.TeacherSelfInfoResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 教师用户基本信息对象映射
 *
 * @author ljz
 * @since 2025-02-07
 */
@Mapper(componentModel = "Spring",imports = {List.class})
public interface TeacherSelfInfoMapping {
    TeacherSelfInfoResponse convert(TeacherSelfInfoResponseDTO source);

    SelfInfoResponse convert(SelfInfoResponseDTO source);
}
