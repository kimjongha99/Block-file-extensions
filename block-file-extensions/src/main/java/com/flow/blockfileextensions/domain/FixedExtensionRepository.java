package com.flow.blockfileextensions.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedExtensionRepository extends JpaRepository<FixedExtension, Long> {
    boolean existsByName(String extension);
}