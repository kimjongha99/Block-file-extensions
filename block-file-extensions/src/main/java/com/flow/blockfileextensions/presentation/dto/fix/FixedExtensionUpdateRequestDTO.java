package com.flow.blockfileextensions.presentation.dto.fix;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FixedExtensionUpdateRequestDTO {

    @NotNull(message = "ID는 필수입니다.")
    private Long id;

    @NotNull(message = "체크 상태는 필수입니다.")
    private Boolean checked;
}