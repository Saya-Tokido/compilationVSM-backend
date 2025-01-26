package com.ljz.compilationVSM.web.soa.admin;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.teacher.AccountManagerIface;
import com.ljz.compilationVSM.api.request.admin.StudentUserCreateRequest;
import com.ljz.compilationVSM.domain.account.service.AccountManagerService;
import com.ljz.compilationVSM.web.convert.AccountMapping;
import lombok.AllArgsConstructor;
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
    public Response<Void> addStudentUser(StudentUserCreateRequest request) {
        accountManagerService.addStudentUser(accountMapping.convert(request));
        return Response.success();
    }
}
