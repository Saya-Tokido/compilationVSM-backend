package com.ljz.compilationVSM.domain.task.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ljz.compilationVSM.common.dto.base.KeyValueDTO;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.domain.convert.QuestionListMapping;
import com.ljz.compilationVSM.domain.dto.OptimizedDTO;
import com.ljz.compilationVSM.domain.facade.AiModelFacade;
import com.ljz.compilationVSM.domain.task.AiQAService;
import com.ljz.compilationVSM.domain.dto.FreeQADTO;
import com.ljz.compilationVSM.domain.dto.OptimCodeDTO;
import com.ljz.compilationVSM.entity.AiQAPO;
import com.ljz.compilationVSM.service.AiQARepository;
import com.ljz.compilationVSM.util.GzipUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AiQAServiceImpl implements AiQAService {
    @Autowired
    private AiModelFacade aiModelFacade;
    @Autowired
    private AiQARepository aiQARepository;
    @Autowired
    private QuestionListMapping questionListMapping;
    @Autowired
    @Qualifier("stringMessageRedisTemplate")
    private RedisTemplate redisTemplate;

    private static final String ANSWER_KEY = "specific_answer";

    public FreeQADTO askByMessage(String question) {
        String answer = aiModelFacade.askByMessage(question);
        FreeQADTO freeQADto = new FreeQADTO(answer);
        return freeQADto;
    }

    public String askByKey(String code) {
        Optional<Object> optional = Optional.ofNullable(redisTemplate.opsForHash().get(ANSWER_KEY, code));
        if (optional.isPresent()) {
            return (String) optional.get();
        } else {
            LambdaQueryWrapper<AiQAPO> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(AiQAPO::getAnswer)
                    .eq(AiQAPO::getId, code);
            Optional<AiQAPO> oneOpt = aiQARepository.getOneOpt(wrapper);
            if (oneOpt.isPresent()) {
                try {
                    String answer = GzipUtil.decompressData(oneOpt.get().getAnswer());
                    redisTemplate.opsForHash().put(ANSWER_KEY, code, answer);
                    return answer;
                } catch (IOException e) {
                    log.error("In AskByKey,Decompress Error!", e);
                    throw new BizException("Decompress Error!");
                }
            } else {
                throw new BizException("No answer for this question!");
            }
        }
    }

    @Override
    public OptimizedDTO optimize(OptimCodeDTO optimCodeDTO) {
        String optimizedCode = aiModelFacade.optimize(optimCodeDTO);
        return new OptimizedDTO(optimizedCode);
    }

    @Override
    public List<KeyValueDTO<String, String>> getQuestionList() {
        LambdaQueryWrapper<AiQAPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(AiQAPO::getId, AiQAPO::getQuestion);
        List<AiQAPO> list = aiQARepository.list(queryWrapper);
        List<KeyValueDTO<String, String>> dtoList = questionListMapping.convert(list);
        return dtoList;
    }
}
