package com.ljz.compilationVSM.api.response.student;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 获取函数列表响应
 * @author ljz
 * @since 2024-12-01
 */
@Data
@AllArgsConstructor
public class MethodListResponse {

    private List<MethodResponse> methodList;

}
