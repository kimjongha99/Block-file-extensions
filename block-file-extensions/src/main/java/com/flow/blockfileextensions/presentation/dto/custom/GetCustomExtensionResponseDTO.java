package com.flow.blockfileextensions.presentation.dto.custom;

import com.flow.blockfileextensions.domain.CustomExtension;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetCustomExtensionResponseDTO {

    private List<CustomExtensionResponseDTO> customExtensionResponseList;
    private Integer listSize;
    private Integer totalPage;
    private Long totalElements;
    private boolean isFirst;
    private boolean isLast;



    public GetCustomExtensionResponseDTO(Page<CustomExtension> customExtensions){
        this.customExtensionResponseList = customExtensions.map(CustomExtensionResponseDTO::new).getContent();
        this.listSize = customExtensions.getNumberOfElements();
        this.totalPage = customExtensions.getTotalPages();
        this.totalElements = customExtensions.getTotalElements();
        this.isFirst = customExtensions.isFirst();
        this.isLast = customExtensions.isLast();
    }
}
