package com.ljz.compilationVSM.api.iface;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.MethodListRequest;
import com.ljz.compilationVSM.api.response.MethodBodyResponse;
import com.ljz.compilationVSM.api.response.MethodListResponse;

/**
 * 代码评估接口
 * @author ljz
 * @since 2024-12-02
 */
public interface OJIface {

    /**
     * 获取函数名列表
     * @param request 获取函数名列表请求
     * @return  函数名列表响应
     */
    Response<MethodListResponse> getMethodList(MethodListRequest request);

    /**
     * 获取函数体
     * @param methodId 函数名id
     * @return 函数体响应
     */
    Response<MethodBodyResponse> getMethodBody(String methodId);
}
