package com.flow.blockfileextensions.application;

import com.flow.blockfileextensions.domain.FixedExtension;
import com.flow.blockfileextensions.domain.FixedExtensionRepository;
import com.flow.blockfileextensions.infrastructure.CustomException;
import com.flow.blockfileextensions.infrastructure.ErrorCode;
import com.flow.blockfileextensions.presentation.dto.fix.FixedExtensionResponseDTO;
import com.flow.blockfileextensions.presentation.dto.fix.FixedExtensionUpdateRequestDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FixedExtensionService {

    private final FixedExtensionRepository fixedExtensionRepository;

    @PostConstruct
    public void init() {
        insertDefaultFixedExtensions();
    }

    private void insertDefaultFixedExtensions() {

        if (fixedExtensionRepository.count() == 0) {
            List<FixedExtension> defaultExtensions = List.of(
                    new FixedExtension(null, ".exe",false),
                    new FixedExtension(null, ".bat",false),
                    new FixedExtension(null, ".cmd",false),
                    new FixedExtension(null, ".sh",false),
                    new FixedExtension(null, ".com",false),
                    new FixedExtension(null, ".scr",false),
                    new FixedExtension(null, ".msi",false),
                    new FixedExtension(null, ".pif",false),
                    new FixedExtension(null, ".cpl",false),
                    new FixedExtension(null, ".hta",false)
            );
            fixedExtensionRepository.saveAll(defaultExtensions);
        }
    }


    public List<FixedExtensionResponseDTO> findAll() {
        List<FixedExtension> fixedExtensions = fixedExtensionRepository.findAll();
        return fixedExtensions.stream()
                .map(FixedExtensionResponseDTO::new)
                .collect(Collectors.toList());
    }



    @Transactional
    public FixedExtensionResponseDTO updateCheckedStatus(FixedExtensionUpdateRequestDTO updateRequestDTO) {
        FixedExtension fixedExtension = fixedExtensionRepository.findById(updateRequestDTO.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        fixedExtension.setChecked(updateRequestDTO.getChecked());
        return new FixedExtensionResponseDTO(fixedExtension);
    }

    @Transactional
    public void clearAllCheckedStatus() {
        List<FixedExtension> fixedExtensions = fixedExtensionRepository.findAll();
        for (FixedExtension fixedExtension : fixedExtensions) {
            fixedExtension.setChecked(false);
        }
        fixedExtensionRepository.saveAll(fixedExtensions);
    }
}
