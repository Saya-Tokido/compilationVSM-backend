package com.ljz.compilationVSM.common.config.redis.protobuf.serializer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.ljz.compilationVSM.common.config.redis.protobuf.outer.LoginUserOuterClass;
import com.ljz.compilationVSM.common.dto.LoginUserDTO;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Optional;

/**
 * redis登录信息序列化器
 *
 * @author ljz
 * @since 2024-12-25
 */
@Slf4j
public class LoginProtobufRedisSerializer implements RedisSerializer<LoginUserDTO> {

    @Override
    public byte[] serialize(LoginUserDTO loginUserDTO) {
        Optional<LoginUserDTO> loginUserOptional = Optional.ofNullable(loginUserDTO);
        if (loginUserOptional.isPresent()) {
            LoginUserOuterClass.LoginUser.Builder builder = LoginUserOuterClass.LoginUser.newBuilder();
            builder.setUserId(loginUserDTO.getUserId())
                    .setUserName(loginUserDTO.getUserName())
                    .setRole(loginUserDTO.getRole())
                    .setLoginTimestamp(loginUserDTO.getLoginTimestamp());
            LoginUserOuterClass.LoginUser message = builder.build();
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
            LoginUserOuterClass.LoginUser message = LoginUserOuterClass.LoginUser.parseFrom(bytes);
            LoginUserDTO loginUserDTO = new LoginUserDTO();
            loginUserDTO.setUserId(message.getUserId());
            loginUserDTO.setUserName(message.getUserName());
            loginUserDTO.setRole(message.getRole());
            loginUserDTO.setLoginTimestamp(message.getLoginTimestamp());
            return loginUserDTO;
        } catch (InvalidProtocolBufferException e) {
            log.error("反序列化用户信息，解析失败");
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
    }
}
