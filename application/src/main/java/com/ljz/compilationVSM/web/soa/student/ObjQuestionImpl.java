package com.ljz.compilationVSM.web.soa.student;


import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.student.ObjQuestionIface;
import com.ljz.compilationVSM.api.request.student.*;
import com.ljz.compilationVSM.api.response.student.*;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.CheckedChooseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.CheckedFillDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ChooseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.FillDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.service.ChooseService;
import com.ljz.compilationVSM.domain.ObjQuestion.service.FillService;
import com.ljz.compilationVSM.web.convert.ObjQuestionMapping;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/student/obj_question")
public class ObjQuestionImpl implements ObjQuestionIface {
    private final ChooseService chooseService;
    private final FillService fillService;
    private final ObjQuestionMapping objQuestionMapping;

    @Override
    @PostMapping("/list")
    public Response<ObjResponse> getObjQuestion(@RequestBody ObjRequest request) {
        List<ChooseDTO> chooseListDTO = chooseService.getChoose(request.getChooseType(), request.getChooseNum());
        List<FillDTO> fillListDTO = fillService.getFill(request.getFillType(), request.getFillNum());
        ObjResponse objResponse = new ObjResponse();
        objResponse.setChooseList(objQuestionMapping.chooseListConvert(chooseListDTO));
        objResponse.setFillList(objQuestionMapping.fillListConvert(fillListDTO));
        return Response.success(objResponse);
    }

    @Override
    @PostMapping("/check")
    public Response<ObjCheckResponse> checkObjQuestion(ObjCheckRequest request) {
        CheckedChooseDTO chooseDTO = chooseService.checkChoose(objQuestionMapping.chooseCheckDTOConvert(request.getChooseAnswer()));
        CheckedFillDTO fillDTO = fillService.checkFill(objQuestionMapping.fillCheckDTOConvert(request.getFillAnswer()));
        return null;
    }

}
