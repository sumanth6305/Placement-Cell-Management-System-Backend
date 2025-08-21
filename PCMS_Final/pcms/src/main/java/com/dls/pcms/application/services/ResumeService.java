package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ResumeService {
    
    Resume saveResume(MultipartFile file, UUID studentId) throws Exception;

    Resume getResume(UUID resumeId) throws Exception;

    void deleteResume(UUID resumeId) throws Exception;
    List<Resume> getAllResumes();
}
