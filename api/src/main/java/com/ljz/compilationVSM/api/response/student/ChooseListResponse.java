package com.ljz.compilationVSM.api.response.student;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChooseListResponse {
    List<ChooseResponse> chooseList;
}
