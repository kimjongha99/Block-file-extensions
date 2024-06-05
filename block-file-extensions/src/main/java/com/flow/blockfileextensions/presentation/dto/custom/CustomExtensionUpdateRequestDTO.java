package com.flow.blockfileextensions.presentation.dto.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomExtensionUpdateRequestDTO {
    private Long id;
    private boolean checked;
}
