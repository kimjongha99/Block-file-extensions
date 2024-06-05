package com.flow.blockfileextensions.presentation.dto.custom;

import com.flow.blockfileextensions.domain.CustomExtension;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomExtensionResponseDTO {
    private Long id;
    private String name;
    private  boolean checked;

    public CustomExtensionResponseDTO(CustomExtension customExtension) {
        this.id = customExtension.getId();
        this.name = customExtension.getName();
        this.checked = customExtension.isChecked();
    }
}