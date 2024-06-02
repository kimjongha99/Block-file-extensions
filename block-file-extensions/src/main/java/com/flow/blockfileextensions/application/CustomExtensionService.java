package com.flow.blockfileextensions.application;


import com.flow.blockfileextensions.domain.CustomExtension;
import com.flow.blockfileextensions.domain.CustomExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomExtensionService {

    private final CustomExtensionRepository customExtensionRepository;

    public List<CustomExtension> findAll() {
        return customExtensionRepository.findAll();
    }

    public Optional<CustomExtension> findById(Long id) {
        return customExtensionRepository.findById(id);
    }

    public CustomExtension save(CustomExtension extension) {
        return customExtensionRepository.save(extension);
    }

    public void delete(Long id) {
        customExtensionRepository.deleteById(id);
    }
}