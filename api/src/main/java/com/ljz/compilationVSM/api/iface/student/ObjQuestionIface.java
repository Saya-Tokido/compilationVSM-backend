package com.ljz.compilationVSM.api.iface.student;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.student.*;
import com.ljz.compilationVSM.api.response.student.*;

/**
 * 客观题接口
 *
 * @author ljz
 * @since 2024-12-05
 */
public interface ObjQuestionIface {

    /**
     * 获取客观题题目
     *
     * @param request 请求
     * @return 客观题响应
     */
    Response<ObjResponse> getObjQuestion(ObjRequest request);

    /**
     * 校验客观题
     *
     * @param request 请求
     * @return 校验结果
     */
    Response<ObjCheckResponse> checkObjQuestion(ObjCheckRequest request);

}
