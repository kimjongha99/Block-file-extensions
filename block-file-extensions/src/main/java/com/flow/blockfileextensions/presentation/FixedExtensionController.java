package com.flow.blockfileextensions.presentation;

import com.flow.blockfileextensions.application.FixedExtensionService;
import com.flow.blockfileextensions.infrastructure.BaseResponse;
import com.flow.blockfileextensions.presentation.dto.fix.FixedExtensionResponseDTO;
import com.flow.blockfileextensions.presentation.dto.fix.FixedExtensionUpdateRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/fixed-extensions")
@RequiredArgsConstructor
public class FixedExtensionController { //TO DO 이름 바꾸기

    private final FixedExtensionService fixedExtensionService;

    @GetMapping
    public BaseResponse<List<FixedExtensionResponseDTO>> getAllFixedExtensions() {
        List<FixedExtensionResponseDTO> fixedExtensions = fixedExtensionService.findAll();
        return BaseResponse.success(fixedExtensions);
    }

    @PutMapping("/{id}")
    public BaseResponse<FixedExtensionResponseDTO> updateFixedExtension(@PathVariable Long id,
                                                                        @RequestBody @Valid FixedExtensionUpdateRequestDTO updateRequestDTO) {
        updateRequestDTO.setId(id); // URL 경로로 받은 ID를 DTO에 설정
        FixedExtensionResponseDTO updatedFixedExtension = fixedExtensionService.updateCheckedStatus(updateRequestDTO);
        return BaseResponse.success(updatedFixedExtension);
    }



    @PutMapping("/clear")
    public BaseResponse<Void> clearAllCheckedStatus() {
        fixedExtensionService.clearAllCheckedStatus();
        return BaseResponse.success();
    }
}