package com.ljz.compilationVSM.util;

import com.google.protobuf.InvalidProtocolBufferException;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.dto.LoginUser;
import com.ljz.compilationVSM.entity.User;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProtobufRedisSerializer implements RedisSerializer<LoginUser> {

    @Override
    public byte[] serialize(LoginUser loginUser) {
        Optional<LoginUser> loginUserOptional = Optional.ofNullable(loginUser);
        if(loginUserOptional.isPresent()){
            LoginUserOuterClass.LoginUser.Builder builder = LoginUserOuterClass.LoginUser.newBuilder();
            User user = loginUser.getUser();
            builder.setUser(
                    LoginUserOuterClass.User.newBuilder()
                            .setUserName(user.getUserName())
                            .setId(user.getId())
                            .setPassword(user.getPassword())
                            .build()
                    );
            Optional<List<String>> permissionsOptional = Optional.ofNullable(loginUser.getPermissions());
            if(permissionsOptional.isPresent()){
                loginUser.getPermissions().stream().forEach(item->builder.addPermission(item));
            }
            LoginUserOuterClass.LoginUser message = builder.build();
            return message.toByteArray();
        }
        return null;
    }

    @Override
    public LoginUser deserialize(byte[] bytes) throws BizException{
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            LoginUserOuterClass.LoginUser message = LoginUserOuterClass.LoginUser.parseFrom(bytes);
            List<String> permissions= new ArrayList<>(message.getPermissionList());
            LoginUserOuterClass.User messageUser = message.getUser();
            return new LoginUser(new User(messageUser.getId(), messageUser.getUserName(), messageUser.getPassword()),permissions);
        } catch (InvalidProtocolBufferException e) {
            throw new BizException(1011,e.toString());
        }
    }
}
