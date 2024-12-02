package com.ljz.compilationVSM.web.soa;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.OJIface;
import com.ljz.compilationVSM.api.request.MethodListRequest;
import com.ljz.compilationVSM.api.response.MethodBodyResponse;
import com.ljz.compilationVSM.api.response.MethodListResponse;
import com.ljz.compilationVSM.domain.oj.dto.MethodBodyResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodListRequestDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodResponseDTO;
import com.ljz.compilationVSM.domain.oj.service.OJService;
import com.ljz.compilationVSM.web.convert.OJMapping;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OJ接口实现
 * @author ljz
 * @since 2024-12-01
 */
@RequestMapping("/master")
@AllArgsConstructor
@RestController
public class OJImpl implements OJIface {

    private final OJService ojService;
    private final OJMapping ojMapping;

    @PostMapping("/question/method_name")
    @Override
    public Response<MethodListResponse> getMethodList(@RequestBody MethodListRequest request) {
        List<MethodResponseDTO> methodList = ojService.getMethodList(new MethodListRequestDTO(request.getLanguage(), request.getCompLanguage()));
        return Response.success(new MethodListResponse(ojMapping.methodResponseListConvert(methodList)));
    }

    @Override
    @GetMapping("/question/method_body/{id}")
    public Response<MethodBodyResponse> getMethodBody(@PathVariable("id") String methodId) {
        MethodBodyResponseDTO methodBody = ojService.getMethodBody(methodId);
        return Response.success(ojMapping.methodBodyResponseConvert(methodBody));
    }
}
