package com.ljz.compilationVSM.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FillListResponse {
    List<FillResponse> fillList;
}
