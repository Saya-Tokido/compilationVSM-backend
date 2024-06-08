package com.ljz.compilationVSM.dependency.dto.base;

import lombok.Data;

@Data
public class BaseResponseDTO<T> {
    Integer code;
    String Message;
    T data;
}
