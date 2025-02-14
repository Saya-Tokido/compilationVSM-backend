package com.ljz.compilationVSM.web.soa.student;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.student.StudentSelfInfoIface;
import com.ljz.compilationVSM.api.response.student.StudentSelfInfoResponse;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.domain.info.dto.StudentSelfInfoResponseDTO;
import com.ljz.compilationVSM.domain.info.service.SelfInfoService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.web.convert.student.StudentSelfInfoMapping;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生用户基本信息接口实现
 *
 * @author ljz
 * @since 2025-02-07
 */
@RequestMapping("/api/student/self")
@AllArgsConstructor
@RestController
public class StudentSelfInfoImpl implements StudentSelfInfoIface {

    private final SelfInfoService selfInfoService;
    private final StudentSelfInfoMapping studentSelfInfoMapping;

    @Override
    @UserAuth(permission = PermissionEnum.STUDENT_SELF_INFO_QUERY)
    @GetMapping
    public Response<StudentSelfInfoResponse> getSelfInfo() {
        StudentSelfInfoResponseDTO responseDTO = selfInfoService.getStudentSelfInfo();
        return Response.success(studentSelfInfoMapping.convert(responseDTO));
    }
}
