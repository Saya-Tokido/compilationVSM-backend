package com.ljz.compilationVSM.web.soa.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.teacher.StudentManagerIface;
import com.ljz.compilationVSM.api.request.teacher.StudentPageQueryRequest;
import com.ljz.compilationVSM.api.response.teacher.StudentInfoPageResponse;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.domain.info.dto.StudentInfoPageResponseDTO;
import com.ljz.compilationVSM.domain.info.service.BasicInfoManagerService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.web.convert.teacher.StudentManagerMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生信息管理接口实现
 *
 * @author ljz
 * @since 2025-01-27
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/teacher/student-manager")
public class StudentManagerImpl implements StudentManagerIface {

    private final StudentManagerMapping studentManagerMapping;
    private final BasicInfoManagerService basicInfoManagerService;

    @Override
    @UserAuth(permission = PermissionEnum.PAGE_QUERY_STUDENT_INFO)
    @PostMapping("/basic-info")
    public Response<StudentInfoPageResponse> pageQueryStudentInfo(@RequestBody StudentPageQueryRequest request) {
        StudentInfoPageResponseDTO responseDTO = basicInfoManagerService.pageQueryStudentInfo(studentManagerMapping.convert(request));
        return Response.success(studentManagerMapping.convert(responseDTO));
    }
}
