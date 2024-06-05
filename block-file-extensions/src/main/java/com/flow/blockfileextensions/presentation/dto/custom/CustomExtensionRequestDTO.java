package com.flow.blockfileextensions.presentation.dto.custom;


import com.flow.blockfileextensions.domain.CustomExtension;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomExtensionRequestDTO {

    @NotBlank(message = "확장자 이름은 필수입니다")
    @Size(max = 20, message = "확장자 이름은 20자 이하여야 합니다.")
    @Pattern(regexp = "^\\.[a-zA-Z]+$", message = "확장자 이름은 '.'으로 시작하고 영어 알파벳만 포함해야 합니다.")
    private String name;

    public CustomExtension toEntity() {
        return CustomExtension.builder()
                .name(this.name)
                .build();
    }
}