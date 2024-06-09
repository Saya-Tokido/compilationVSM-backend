package com.ljz.compilationVSM.domain.protobuf.serializer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.protobuf.outer.LoginUserDTOOuterClass;
import com.ljz.compilationVSM.domain.dto.LoginUserDTO;
import com.ljz.compilationVSM.infrastructure.po.UserPO;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginProtobufRedisSerializer implements RedisSerializer<LoginUserDTO> {

    @Override
    public byte[] serialize(LoginUserDTO loginUserDTO) {
        Optional<LoginUserDTO> loginUserOptional = Optional.ofNullable(loginUserDTO);
        if (loginUserOptional.isPresent()) {
            UserPO userPO = loginUserDTO.getUserPO();
            LoginUserDTOOuterClass.LoginUserDTO.Builder builder = LoginUserDTOOuterClass.LoginUserDTO.newBuilder();
            builder.setUser(
                    LoginUserDTOOuterClass.UserPO.newBuilder()
                            .setUserName(userPO.getUserName())
                            .setId(userPO.getId())
                            .setPassword(userPO.getPassword())
                            .build()
            );
            Optional<List<String>> permissionsOptional = Optional.ofNullable(loginUserDTO.getPermissions());
            if (permissionsOptional.isPresent()) {
                loginUserDTO.getPermissions().forEach(builder::addPermission);
            }
            LoginUserDTOOuterClass.LoginUserDTO message = builder.build();
            return message.toByteArray();
        }
        return null;
    }

    @Override
    public LoginUserDTO deserialize(byte[] bytes) throws BizException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            LoginUserDTOOuterClass.LoginUserDTO message = LoginUserDTOOuterClass.LoginUserDTO.parseFrom(bytes);
            List<String> permissions = new ArrayList<>(message.getPermissionList());
            LoginUserDTOOuterClass.UserPO messageUser = message.getUser();
            return new LoginUserDTO(new UserPO(messageUser.getId(), messageUser.getUserName(), messageUser.getPassword()), permissions);
        } catch (InvalidProtocolBufferException e) {
            throw new BizException(1011, e.toString());
        }
    }
}
