package com.flow.blockfileextensions.application;

import com.flow.blockfileextensions.domain.FixedExtension;
import com.flow.blockfileextensions.domain.FixedExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FixedExtensionService {

    private  final  FixedExtensionRepository fixedExtensionRepository;


    public List<FixedExtension> findAll() {
        return fixedExtensionRepository.findAll();
    }

    public Optional<FixedExtension> findById(Long id) {
        return fixedExtensionRepository.findById(id);
    }

    public FixedExtension save(FixedExtension extension) {
        return fixedExtensionRepository.save(extension);
    }

    public void delete(Long id) {
        fixedExtensionRepository.deleteById(id);
    }
}
