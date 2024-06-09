package com.ljz.compilationVSM.web.convert;

import com.ljz.compilationVSM.api.request.LoginRequest;
import com.ljz.compilationVSM.api.response.LoginResponse;
import com.ljz.compilationVSM.domain.dto.LoggedDTO;
import com.ljz.compilationVSM.domain.dto.LoginDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapping {
    LoginDTO convert(LoginRequest source);

    LoginResponse convert2(LoggedDTO source);
}
