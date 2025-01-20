package com.ljz.compilationVSM.domain.convert;

import com.ljz.compilationVSM.common.dto.LexerTestCaseDTO;
import com.ljz.compilationVSM.common.enums.LevelEnum;
import com.ljz.compilationVSM.domain.oj.dto.LexerProblemResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodBodyResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.MethodResponseDTO;
import com.ljz.compilationVSM.infrastructure.po.LexerTestcasePO;
import com.ljz.compilationVSM.infrastructure.po.MethodBodyPO;
import com.ljz.compilationVSM.infrastructure.po.MethodNamePO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring",imports = {List.class,LevelEnum.class})
public interface OJDTOMapping {

    List<MethodResponseDTO> methodListConvert(List<MethodNamePO> source);

    @Mappings({
            @Mapping(target = "level",expression = "java(LevelEnum.getByCode(source.getLevel()).getName())"),
            @Mapping(target = "commitNum",expression = "java(source.getCommitNum().toString())"),
            @Mapping(target = "passPercent", expression = "java(String.format(\"%.2f\", 1.0 * source.getPassNum() / source.getCommitNum() * 100)+\"%\")")
    })
    MethodResponseDTO methodConvert(MethodNamePO source);

    MethodBodyResponseDTO methodBodyConvert(MethodBodyPO source);

    @Mappings({
            @Mapping(target = "lexerId",expression = "java(source.getLexerId().toString())")
    })
    LexerProblemResponseDTO lexerProblemConvert(LexerTestcasePO source);

    List<LexerTestCaseDTO> lexerTestCaseConvert(List<LexerTestcasePO> source);
}
