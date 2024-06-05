package com.flow.blockfileextensions.application;


import com.flow.blockfileextensions.domain.CustomExtension;
import com.flow.blockfileextensions.domain.CustomExtensionRepository;
import com.flow.blockfileextensions.domain.FixedExtensionRepository;
import com.flow.blockfileextensions.infrastructure.CustomException;
import com.flow.blockfileextensions.infrastructure.ErrorCode;
import com.flow.blockfileextensions.presentation.dto.custom.CustomExtensionRequestDTO;
import com.flow.blockfileextensions.presentation.dto.custom.CustomExtensionResponseDTO;
import com.flow.blockfileextensions.presentation.dto.custom.CustomExtensionUpdateRequestDTO;
import com.flow.blockfileextensions.presentation.dto.custom.GetCustomExtensionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomExtensionService {
    private final CustomExtensionRepository customExtensionRepository;
    private final FixedExtensionRepository fixedExtensionRepository;


    public CustomExtensionResponseDTO addCustomExtension(CustomExtensionRequestDTO requestDTO) {
        String name = requestDTO.getName();
        // CustomExtension 또는 FixExtension에 확장이 이미 존재하는지 확인합니다.
        if (customExtensionRepository.existsByName(name) || fixedExtensionRepository.existsByName(name)) {
            throw new CustomException(ErrorCode.DUPLICATE_EXTENSION);
        }


        CustomExtension customExtension = requestDTO.toEntity();
        CustomExtension createdCustomExtension = customExtensionRepository.save(customExtension);
        return new CustomExtensionResponseDTO(createdCustomExtension);

    }


    public GetCustomExtensionResponseDTO getCustomExtensions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomExtension> customExtensions = customExtensionRepository.findAll(pageable);
        if (customExtensionRepository.count() > 200) {
            throw new CustomException(ErrorCode.CUSTOM_EXTENSION_LIMIT_EXCEEDED);
        }


        return new GetCustomExtensionResponseDTO(customExtensions);
    }
    @Transactional
    public CustomExtensionResponseDTO updateCheckedStatus(CustomExtensionUpdateRequestDTO updateRequestDTO) {
        CustomExtension customExtension = customExtensionRepository.findById(updateRequestDTO.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        customExtension.setChecked(updateRequestDTO.isChecked());
        return new CustomExtensionResponseDTO(customExtension);
    }

    @Transactional
    public void clearAllCheckedStatus() {
        List<CustomExtension> customExtensions = customExtensionRepository.findAll();
        customExtensions.forEach(extension -> extension.setChecked(false));
        customExtensionRepository.saveAll(customExtensions);
    }

    @Transactional
    public void deleteCustomExtension(Long id) {
        CustomExtension customExtension = customExtensionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        customExtensionRepository.delete(customExtension);
    }
}