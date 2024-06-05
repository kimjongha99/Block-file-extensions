package com.flow.blockfileextensions.presentation.dto.fix;

import com.flow.blockfileextensions.domain.FixedExtension;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FixedExtensionResponseDTO {
    private Long id;
    private String name;

    private  boolean checked;


    public FixedExtensionResponseDTO(FixedExtension fixedExtension) {
        this.id = fixedExtension.getId();
        this.name = fixedExtension.getName();
        this.checked = fixedExtension.isChecked();

    }
}