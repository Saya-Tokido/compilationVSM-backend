package com.ljz.compilationVSM.web.soa.admin;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.admin.AccountManagerIface;
import com.ljz.compilationVSM.api.request.admin.AccountDeleteRequest;
import com.ljz.compilationVSM.api.request.admin.StudentPageQueryRequest;
import com.ljz.compilationVSM.api.request.admin.StudentUserCreateRequest;
import com.ljz.compilationVSM.api.request.admin.TeacherUserCreateRequest;
import com.ljz.compilationVSM.api.response.admin.StudentInfoPageResponse;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.common.utils.ExcelUtil;
import com.ljz.compilationVSM.domain.account.service.AccountManagerService;
import com.ljz.compilationVSM.domain.info.dto.StudentInfoPageResponseDTO;
import com.ljz.compilationVSM.domain.info.service.BasicInfoManagerService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.web.convert.admin.AccountMapping;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * 账号管理接口实现
 *
 * @author ljz
 * @since 2025-01-20
 */
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/admin/account")
public class AccountManagerImpl implements AccountManagerIface {

    private final AccountManagerService accountManagerService;
    private final BasicInfoManagerService basicInfoManagerService;
    private final AccountMapping accountMapping;
    private final ExcelUtil excelUtil;

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

    @Override
    @PostMapping("/add-student-by-excel")
    @UserAuth(permission = PermissionEnum.CREATE_STUDENT_ACCOUNT_BY_EXCEL)
    public Response<Void> addStudentByExcel(@RequestBody MultipartFile file) {
        try {
            List<StudentUserCreateRequest> request = excelUtil.importFromExcel(file.getBytes(), StudentUserCreateRequest.class);
            accountManagerService.addStudentUserBatch(accountMapping.convert(request));
            return Response.success();
        } catch (Exception ex) {
            if (ex instanceof BizException bizException) {
                throw bizException;
            } else {
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                log.error("管理员通过Excel批量添加学生信息, 触发已检查异常, 异常信息\n {}", sw);
                throw new BizException(BizExceptionCodeEnum.EXCEL_FORMAT_ERROR);
            }
        }
    }

    @Override
    @UserAuth(permission = PermissionEnum.ADMIN_PAGE_QUERY_STUDENT)
    @PostMapping("/student-page")
    public Response<StudentInfoPageResponse> pageQueryStudent(@RequestBody StudentPageQueryRequest request) {
        StudentInfoPageResponseDTO responseDTO = basicInfoManagerService.pageQueryStudentInfoByAdmin(accountMapping.convert(request));
        return Response.success(accountMapping.convert(responseDTO));
    }

    @Override
    @UserAuth(permission = PermissionEnum.DELETE_STUDENT_ACCOUNT)
    @PostMapping("/delete-student")
    public Response<Void> deleteStudent(@RequestBody AccountDeleteRequest request) {
        accountManagerService.deleteStudentAccount(request.getNumber());
        return Response.success();
    }

    @Override
    @UserAuth(permission = PermissionEnum.DELETE_TEACHER_ACCOUNT)
    @PostMapping("/delete-teacher")
    public Response<Void> deleteTeacher(@RequestBody AccountDeleteRequest request) {
        accountManagerService.deleteTeacherAccount(request.getNumber());
        return Response.success();
    }
}
