package com.ljz.compilationVSM.web.soa.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.teacher.TeacherSelfInfoIface;
import com.ljz.compilationVSM.api.response.teacher.TeacherSelfInfoResponse;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.domain.info.dto.TeacherSelfInfoResponseDTO;
import com.ljz.compilationVSM.domain.info.service.SelfInfoService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.web.convert.teacher.TeacherSelfInfoMapping;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 教师用户基本信息接口实现
 *
 * @author ljz
 * @since 2025-02-07
 */
@RequestMapping("/api/teacher/self")
@AllArgsConstructor
@RestController
public class TeacherSelfInfoImpl implements TeacherSelfInfoIface {

    private final SelfInfoService selfInfoService;
    private final TeacherSelfInfoMapping teacherSelfInfoMapping;

    @Override
    @UserAuth(permission = PermissionEnum.TEACHER_SELF_INFO_QUERY)
    @GetMapping
    public Response<TeacherSelfInfoResponse> getSelfInfo() {
        TeacherSelfInfoResponseDTO responseDTO = selfInfoService.getTeacherSelfInfo();
        return Response.success(teacherSelfInfoMapping.convert(responseDTO));
    }
}
