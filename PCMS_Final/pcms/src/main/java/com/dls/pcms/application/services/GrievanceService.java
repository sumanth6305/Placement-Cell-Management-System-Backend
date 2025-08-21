package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.Grievance;
import com.dls.pcms.domain.models.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface GrievanceService {

    Grievance saveGrievance(MultipartFile file, UUID studentId, String description, String subject) throws Exception;

    Grievance getGrievance(UUID grievanceId) throws Exception;

    void deleteGrievance(UUID grievanceId) throws Exception;

    List<Grievance> getAllGrievances();
    Page<Grievance> getAllGrievancesPage(Pageable pageable);

    Grievance findById(UUID grievanceId);
}
