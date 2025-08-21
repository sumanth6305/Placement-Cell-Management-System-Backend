package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.application.dto.ResumeDTO;
import com.dls.pcms.application.services.ResumeService;
import com.dls.pcms.domain.models.Resume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/resumes")
@Slf4j
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResumeDTO> uploadResume(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("studentId") UUID studentId) throws Exception {
        log.info("Handling request to upload resume for studentId: {}", studentId);

        Resume resume = resumeService.saveResume(file, studentId);
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/resumes/download/")
                .path(resume.getResumeId().toString())
                .toUriString();

        ResumeDTO resumeDTO = new ResumeDTO(
                resume.getResumeId(),
                resume.getStudent().getStudentId(),
                resume.getResumeFilename(),
                resume.getUploadedAt(),
                resume.getUpdatedAt()
        );

        log.info("Resume uploaded successfully with ID: {}", resume.getResumeId());
        log.info("Download URL: {}", downloadURL);

        return new ResponseEntity<>(resumeDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Resume>> getAllResumes() {
        log.info("Handling request to fetch all resumes");
        List<Resume> resumes = resumeService.getAllResumes();
        log.info("Returning {} resumes", resumes.size());
        return new ResponseEntity<>(resumes, HttpStatus.OK);
    }

    @GetMapping("/download/{resumeId}")
    public ResponseEntity<Resource> downloadResume(@PathVariable UUID resumeId) throws Exception {
        log.info("Handling request to download resume with ID: {}", resumeId);
        Resume resume = resumeService.getResume(resumeId);
        log.info("Resume {} found for download", resumeId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resume.getResumeFilename() + "\"")
                .body(new ByteArrayResource(resume.getResumeFile()));
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteResume(@PathVariable UUID resumeId) throws Exception {
        log.info("Handling request to delete resume with ID: {}", resumeId);
        resumeService.deleteResume(resumeId);
        log.info("Resume {} deleted successfully", resumeId);
        return ResponseEntity.noContent().build();
    }
}

/*package com.dls.pcms.adapters.inbound.controllers;


import com.dls.pcms.application.dto.ResumeDTO;
import com.dls.pcms.application.services.ResumeService;
import com.dls.pcms.domain.models.Resume;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResumeDTO> uploadResume(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("studentId") UUID studentId) throws Exception {
        Resume resume = resumeService.saveResume(file, studentId);
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/resumes/download/")
                .path(resume.getResumeId().toString())
                .toUriString();

        ResumeDTO resumeDTO = new ResumeDTO(
                resume.getResumeId(),
                resume.getStudent().getStudentId(),
                resume.getResumeFilename(),
                resume.getUploadedAt(),
                resume.getUpdatedAt()
        );

        return new ResponseEntity<>(resumeDTO, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Resume>> getAllResumes() {
       // log.info("Handling request to fetch all resumes");
        List<Resume> resumes = resumeService.getAllResumes();
        //log.info("Returning {} resumes", resumes.size());
        return new ResponseEntity<>(resumes, HttpStatus.OK);
    }

    @GetMapping("/download/{resumeId}")
    public ResponseEntity<Resource> downloadResume(@PathVariable UUID resumeId) throws Exception {
        Resume resume = resumeService.getResume(resumeId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resume.getResumeFilename() + "\"")
                .body(new ByteArrayResource(resume.getResumeFile()));
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<Void> deleteResume(@PathVariable UUID resumeId) throws Exception {
        resumeService.deleteResume(resumeId);
        return ResponseEntity.noContent().build();
    }
}


 */