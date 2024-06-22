package com.ljz.compilationVSM.domain.aiQA.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ljz.compilationVSM.common.dto.base.KeyValueDTO;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.dependency.facade.AiModelFacade;
import com.ljz.compilationVSM.domain.aiQA.dto.*;
import com.ljz.compilationVSM.domain.convert.AiModelFacadeMapping;
import com.ljz.compilationVSM.domain.convert.QuestionListMapping;
import com.ljz.compilationVSM.domain.aiQA.service.AiQAService;
import com.ljz.compilationVSM.domain.utils.GzipUtil;
import com.ljz.compilationVSM.infrastructure.po.AiQAPO;
import com.ljz.compilationVSM.infrastructure.repository.AiQARepository;
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
    private AiModelFacadeMapping aiModelFacadeMapping;
    @Autowired
    @Qualifier("stringMessageRedisTemplate")
    private RedisTemplate redisTemplate;

    private static final String ANSWER_KEY = "specific_answer";

    public FreeQAAnswerDTO askByMessage(FreeQAQuestionDTO question) {
        String answer = aiModelFacade.askByMessage(question.getQuestion());
        FreeQAAnswerDTO freeQADto = new FreeQAAnswerDTO(answer);
        return freeQADto;
    }

    public SpecificAnswerDTO askByKey(SpecificQuestionDTO questionDTO) {
        Optional<Object> optional = Optional.ofNullable(redisTemplate.opsForHash().get(ANSWER_KEY, questionDTO.getQuestionKey()));
        if (optional.isPresent()) {
            return new SpecificAnswerDTO((String) optional.get());
        } else {
            LambdaQueryWrapper<AiQAPO> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(AiQAPO::getAnswer)
                    .eq(AiQAPO::getId, questionDTO.getQuestionKey());
            Optional<AiQAPO> oneOpt = aiQARepository.getOneOpt(wrapper);
            if (oneOpt.isPresent()) {
                try {
                    String answer = GzipUtil.decompressData(oneOpt.get().getAnswer());
                    redisTemplate.opsForHash().put(ANSWER_KEY, questionDTO.getQuestionKey(), answer);
                    return new SpecificAnswerDTO(answer);
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
        String optimizedCode = aiModelFacade.optimize(aiModelFacadeMapping.convert(optimCodeDTO));
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
