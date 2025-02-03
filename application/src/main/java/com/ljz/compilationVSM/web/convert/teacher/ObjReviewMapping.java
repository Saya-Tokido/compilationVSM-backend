package com.ljz.compilationVSM.web.convert.teacher;

import com.ljz.compilationVSM.api.request.teacher.ObjScoreModifyRequest;
import com.ljz.compilationVSM.api.response.teacher.ObjAnswerInfoResponse;
import com.ljz.compilationVSM.api.response.teacher.StudentBaseInfoResponse;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ObjAnswerInfoResponseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ObjScoreModifyRequestDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.StudentBaseInfoResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 客观题答题情况评审接口对象映射
 *
 * @author ljz
 * @since 2025-01-30
 */
@Mapper(componentModel = "spring", imports = {List.class})
public interface ObjReviewMapping {

    ObjAnswerInfoResponse convert(ObjAnswerInfoResponseDTO source);

    StudentBaseInfoResponse convert(StudentBaseInfoResponseDTO source);

    ObjScoreModifyRequestDTO convert(ObjScoreModifyRequest source);

}
