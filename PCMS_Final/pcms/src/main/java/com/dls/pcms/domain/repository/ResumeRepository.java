package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {
}
