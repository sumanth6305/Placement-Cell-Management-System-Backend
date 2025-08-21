package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.Grievance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GrievanceRepository extends JpaRepository<Grievance, UUID> {
}
