package com.ljz.compilationVSM.web.soa.admin;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.admin.AccountManagerIface;
import com.ljz.compilationVSM.api.request.admin.StudentUserCreateRequest;
import com.ljz.compilationVSM.api.request.admin.TeacherUserCreateRequest;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.domain.account.service.AccountManagerService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.web.convert.admin.AccountMapping;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号管理接口实现
 *
 * @author ljz
 * @since 2025-01-20
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/account")
public class AccountManagerImpl implements AccountManagerIface {

    private final AccountManagerService accountManagerService;
    private final AccountMapping accountMapping;

    @Override
    @PostMapping("/add-student")
    @UserAuth(permission = PermissionEnum.CREATE_STUDENT_ACCOUNT)
    public Response<Void> addStudentUser(@RequestBody StudentUserCreateRequest request) {
        accountManagerService.addStudentUser(accountMapping.convert(request));
        return Response.success();
    }

    @Override
    @PostMapping("/add-teacher")
    @UserAuth(permission = PermissionEnum.CREATE_TEACHER_ACCOUNT)
    public Response<Void> addTeacherUser(@RequestBody TeacherUserCreateRequest request) {
        accountManagerService.addTeacherUser(accountMapping.convert(request));
        return Response.success();
    }
}
