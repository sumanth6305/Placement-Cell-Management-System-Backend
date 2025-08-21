package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
