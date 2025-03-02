package com.ljz.compilationVSM.domain.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ChooseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ChoosePageQueryResponseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.FillDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.FillPageQueryResponseDTO;
import com.ljz.compilationVSM.infrastructure.po.ChoosePO;
import com.ljz.compilationVSM.infrastructure.po.FillPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", imports = {List.class})
public interface ObjQuestionDTOMapping {
    List<ChooseDTO> chooseListConvert(List<ChoosePO> source);

    List<FillDTO> fillListConvert(List<FillPO> source);

    @Mappings({
            @Mapping(target = "totalPages", expression = "java((int)source.getPages())"),
            @Mapping(target = "currentPage", expression = "java((int)source.getCurrent())"),
            @Mapping(target = "totalRecords", expression = "java((int)source.getTotal())")
    })
    ChoosePageQueryResponseDTO convertPage(Page<ChoosePO> source);

    List<ChoosePageQueryResponseDTO.Choose> convertList(List<ChoosePO> source);

    @Mappings({
            @Mapping(target = "totalPages", expression = "java((int)source.getPages())"),
            @Mapping(target = "currentPage", expression = "java((int)source.getCurrent())"),
            @Mapping(target = "totalRecords", expression = "java((int)source.getTotal())")
    })
    FillPageQueryResponseDTO convertPage2(Page<FillPO> source);

    List<FillPageQueryResponseDTO.Fill> convertList2(List<FillPO> source);

}
