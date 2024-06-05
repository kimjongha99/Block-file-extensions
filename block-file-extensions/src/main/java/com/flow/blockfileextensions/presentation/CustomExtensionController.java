package com.flow.blockfileextensions.presentation;

import com.flow.blockfileextensions.application.CustomExtensionService;
import com.flow.blockfileextensions.domain.CustomExtension;
import com.flow.blockfileextensions.infrastructure.BaseResponse;
import com.flow.blockfileextensions.presentation.dto.custom.CustomExtensionRequestDTO;
import com.flow.blockfileextensions.presentation.dto.custom.CustomExtensionResponseDTO;
import com.flow.blockfileextensions.presentation.dto.custom.CustomExtensionUpdateRequestDTO;
import com.flow.blockfileextensions.presentation.dto.custom.GetCustomExtensionResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/custom-extensions")
@RequiredArgsConstructor
public class CustomExtensionController {

    private final CustomExtensionService customExtensionService;

    @PostMapping
    public BaseResponse<CustomExtensionResponseDTO> addCustomExtension(@RequestBody @Valid CustomExtensionRequestDTO requestDTO) {
        CustomExtensionResponseDTO responseDTO = customExtensionService.addCustomExtension(requestDTO);
        return BaseResponse.success(responseDTO);

    }

    @GetMapping
    public BaseResponse<GetCustomExtensionResponseDTO> getAllCustomExtensions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        GetCustomExtensionResponseDTO responseDTO = customExtensionService.getCustomExtensions(page, size);
        return BaseResponse.success(responseDTO);
    }

    @PutMapping("/{id}")
    public BaseResponse<CustomExtensionResponseDTO> updateCheckedStatus(@PathVariable Long id, @RequestBody CustomExtensionUpdateRequestDTO updateRequestDTO) {
        updateRequestDTO.setId(id); // 경로 변수로 받은 ID를 DTO에 설정
        CustomExtensionResponseDTO responseDTO = customExtensionService.updateCheckedStatus(updateRequestDTO);
        return BaseResponse.success(responseDTO);
    }

    @PutMapping("/clear")
    public BaseResponse<Void> clearAllCheckedStatus() {
        customExtensionService.clearAllCheckedStatus();
        return BaseResponse.success();
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteCustomExtension(@PathVariable Long id) {
        customExtensionService.deleteCustomExtension(id);
        return BaseResponse.success();
    }
    }