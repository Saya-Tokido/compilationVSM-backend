package com.ljz.compilationVSM.domain.ObjQuestion.service;

import com.ljz.compilationVSM.domain.ObjQuestion.dto.*;

public interface ObjQuestionService {
    /**
     * 获取客观题题目
     *
     * @param objQueryDTO 请求入参
     * @return 选择填空题题目
     */
    ObjResponseDTO getObjQuestion(ObjQueryDTO objQueryDTO);

    /**
     * 校验客观题答案
     *
     * @param requestDTO 请求入参
     * @return 校验结果
     */
    ObjCheckResponseDTO checkObjQuestion(ObjCheckRequestDTO requestDTO);

    /**
     * 获取学生客观题答题情况
     *
     * @param number 学生学号
     * @return 客观题答题情况
     */
    ObjAnswerInfoResponseDTO getObjAnswerInfo(String number);

    /**
     * 客观题调分
     *
     * @param requestDTO 请求参数
     */
    void modifyObjScore(ObjScoreModifyRequestDTO requestDTO);

    /**
     * 获取选择题题库分页
     *
     * @param requestDTO 请求参数
     * @return 选择题题库分页
     */
    ChoosePageQueryResponseDTO pageChooseBank(ChoosePageQueryRequestDTO requestDTO);

    /**
     * 获取填空题题库分页
     *
     * @param requestDTO 请求参数
     * @return 填空题题库分页
     */
    FillPageQueryResponseDTO pageFillBank(FillPageQueryRequestDTO requestDTO);

    /**
     * 删除选择题
     * @param id 选择题id
     */
    void deleteChoose(Long id);

    /**
     * 删除填空题
     * @param id 填空题id
     */
    void deleteFill(Long id);
}
