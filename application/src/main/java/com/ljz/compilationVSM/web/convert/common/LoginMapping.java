package com.ljz.compilationVSM.web.convert.common;

import com.ljz.compilationVSM.api.request.common.LoginRequest;
import com.ljz.compilationVSM.api.response.common.LoginResponse;
import com.ljz.compilationVSM.domain.login.dto.LoggedDTO;
import com.ljz.compilationVSM.domain.login.dto.LoginDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapping {
    LoginDTO convert(LoginRequest source);

    LoginResponse convert2(LoggedDTO source);
}
