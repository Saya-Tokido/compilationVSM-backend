package com.ljz.compilationVSM.common.config.redis.protobuf.serializer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.ljz.compilationVSM.common.config.redis.protobuf.outer.LexerTestCaseOuterClass;
import com.ljz.compilationVSM.common.dto.LexerTestCaseDTO;
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
public class LexerTestCaseProtobufRedisSerializer implements RedisSerializer<LexerTestCaseDTO> {

    @Override
    public byte[] serialize(LexerTestCaseDTO lexerTestCaseDTO) {
        Optional<LexerTestCaseDTO> lexerTestCaseDTOOptional = Optional.ofNullable(lexerTestCaseDTO);
        if (lexerTestCaseDTOOptional.isPresent()) {
            LexerTestCaseOuterClass.LexerTestCase.Builder builder = LexerTestCaseOuterClass.LexerTestCase.newBuilder();
            builder.setTerminalInput(lexerTestCaseDTO.getTerminalInput())
                    .setTerminaOutput(lexerTestCaseDTO.getTerminalOutput());
            LexerTestCaseOuterClass.LexerTestCase message = builder.build();
            return message.toByteArray();
        }
        return null;
    }

    @Override
    public LexerTestCaseDTO deserialize(byte[] bytes) throws BizException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            LexerTestCaseOuterClass.LexerTestCase message = LexerTestCaseOuterClass.LexerTestCase.parseFrom(bytes);
            LexerTestCaseDTO lexerTestCaseDTO = new LexerTestCaseDTO();
            lexerTestCaseDTO.setTerminalInput(message.getTerminalInput());
            lexerTestCaseDTO.setTerminalOutput(message.getTerminaOutput());
            return lexerTestCaseDTO;
        } catch (InvalidProtocolBufferException e) {
            log.error("反序列化词法分析器用例，解析失败");
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
    }
}
