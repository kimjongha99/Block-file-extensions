package com.flow.blockfileextensions.presentation.dto;

import com.flow.blockfileextensions.domain.CustomExtension;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomExtensionDTO {
    private String extension;

    public CustomExtension toEntity() {
        return CustomExtension.builder()
                .extension(extension)
                .build();
    }
}
