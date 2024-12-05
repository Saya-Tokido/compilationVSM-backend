package com.ljz.compilationVSM.web.soa;


import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.ObjQuestionIface;
import com.ljz.compilationVSM.api.request.ChooseCheckRequest;
import com.ljz.compilationVSM.api.request.ChooseRequest;
import com.ljz.compilationVSM.api.request.FillCheckRequest;
import com.ljz.compilationVSM.api.request.FillRequest;
import com.ljz.compilationVSM.api.response.*;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.CheckedChooseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.CheckedFillDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ChooseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.FillDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.service.ChooseService;
import com.ljz.compilationVSM.domain.ObjQuestion.service.FillService;
import com.ljz.compilationVSM.web.convert.ObjQuestionMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController

@RequestMapping("/obj_question")
public class ObjQuestionImpl implements ObjQuestionIface {
    private final ChooseService chooseService;
    private final FillService fillService;
    private final ObjQuestionMapping objQuestionMapping;

    @Autowired
    ObjQuestionImpl(ChooseService chooseService,FillService fillService,ObjQuestionMapping objQuestionMapping){
        this.chooseService=chooseService;
        this.fillService=fillService;
        this.objQuestionMapping=objQuestionMapping;
    }

    @Override
    @PostMapping("/choose_list")
    public Response<ChooseListResponse> getChoose(@RequestBody ChooseRequest request) {
        List<ChooseDTO> chooseListDTO = chooseService.getChoose(request.getType(), request.getNum());
        return Response.success(new ChooseListResponse(objQuestionMapping.chooseListConvert(chooseListDTO)));
    }

    @Override
    @PostMapping("/fill_list")
    public Response<FillListResponse> getFill(@RequestBody FillRequest request) {
        List<FillDTO> fillListDTO = fillService.getFill(request.getType(), request.getNum());
        return Response.success(new FillListResponse(objQuestionMapping.fillListConvert(fillListDTO)));
    }

    @Override
    @PostMapping("/choose_check")
    public Response<ChooseCheckResponse> checkChoose(@RequestBody ChooseCheckRequest request) {
        CheckedChooseDTO chooseDTO = chooseService.checkChoose(objQuestionMapping.chooseCheckDTOConvert(request));
        return Response.success(objQuestionMapping.chooseCheckedConvert(chooseDTO));
    }

    @Override
    @PostMapping("/fill_check")
    public Response<FillCheckResponse> checkFill(@RequestBody FillCheckRequest request) {
        CheckedFillDTO fillDTO = fillService.checkFill(objQuestionMapping.fillCheckDTOConvert(request));
        return Response.success(objQuestionMapping.fillCheckedConvert(fillDTO));
    }

}
