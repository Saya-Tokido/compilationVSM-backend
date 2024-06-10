package com.ljz.compilationVSM.web.soa;


import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.QuestionIface;
import com.ljz.compilationVSM.api.request.ChooseRequest;
import com.ljz.compilationVSM.api.request.FillRequest;
import com.ljz.compilationVSM.api.response.ChooseListResponse;
import com.ljz.compilationVSM.api.response.FillListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController

@RequestMapping("/obj_question")
public class QuestionImpl implements QuestionIface {
    @Autowired
    private ChooseService chooseService;
    @Autowired
    private FillService fillService;


    @Override
    public Response<ChooseListResponse> getChoose(ChooseRequest request) {
        return null;
    }

    @Override
    public Response<FillListResponse> getFill(FillRequest request) {
        return null;
    }
}
