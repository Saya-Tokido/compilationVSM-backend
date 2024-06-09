package com.ljz.compilationVSM.common.protobuf.serializer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.protobuf.outer.StringMessageOuterClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Optional;

/**
 * string protobuf redis serializer
 */
@Slf4j
public class StringProtobufRedisSerializer implements RedisSerializer<String> {

    @Override
    public byte[] serialize(String message) {
        Optional<String> messageOptional = Optional.ofNullable(message);
        if(messageOptional.isPresent()){
            StringMessageOuterClass.StringMessage.Builder builder = StringMessageOuterClass.StringMessage.newBuilder();
            builder.setMessage(message);
            StringMessageOuterClass.StringMessage build = builder.build();
            return build.toByteArray();
        }
        return  null;
    }

    @Override
    public String deserialize(byte[] bytes) throws BizException{
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            StringMessageOuterClass.StringMessage message = StringMessageOuterClass.StringMessage.parseFrom(bytes);
            return message.getMessage();
        } catch (InvalidProtocolBufferException e) {
            throw new BizException(1011,e.toString());
        }
    }
}
