package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.application.dto.GrievanceDTO;
import com.dls.pcms.application.services.GrievanceService;
import com.dls.pcms.domain.models.Grievance;
import com.dls.pcms.domain.models.InterviewSchedule;
import com.dls.pcms.domain.models.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/grievances")
@Slf4j
public class GrievanceController {

    private final GrievanceService grievanceService;

    public GrievanceController(GrievanceService grievanceService) {
        this.grievanceService = grievanceService;
    }

    @PostMapping("/upload")
    public ResponseEntity<GrievanceDTO> uploadGrievance(@RequestParam("file") MultipartFile file,
                                                        @RequestParam("studentId") UUID studentId,
                                                        @RequestParam("description") String description,
                                                        @RequestParam("subject") String subject) throws Exception {
        log.info("Handling request to upload grievance for studentId: {}", studentId);

        Grievance grievance = grievanceService.saveGrievance(file, studentId, description, subject);
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/grievances/download/")
                .path(grievance.getGrievanceId().toString())
                .toUriString();

        GrievanceDTO grievanceDTO = new GrievanceDTO(
                grievance.getGrievanceId(),
                grievance.getStudent().getStudentId(),
                grievance.getSubject(),
                grievance.getDescription(),
                grievance.getStatus(),
                grievance.getResumeFilename()
        );

        log.info("Grievance uploaded successfully with ID: {}", grievance.getGrievanceId());
        log.info("Download URL: {}", downloadURL);

        return new ResponseEntity<>(grievanceDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Grievance>> getAllGrievances() {
        log.info("Handling request to fetch all grievances");
        List<Grievance> grievances = grievanceService.getAllGrievances();
        log.info("Returning {} grievances", grievances.size());
        return new ResponseEntity<>(grievances, HttpStatus.OK);
    }

    @GetMapping("/download/{grievanceId}")
    public ResponseEntity<Resource> downloadGrievance(@PathVariable UUID grievanceId) throws Exception {
        log.info("Handling request to download grievance with ID: {}", grievanceId);
        Grievance grievance = grievanceService.getGrievance(grievanceId);
        log.info("Grievance {} found for download", grievanceId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + grievance.getResumeFilename() + "\"")
                .body(new ByteArrayResource(grievance.getResumeFile()));
    }

    @DeleteMapping("/{grievanceId}")
    public ResponseEntity<Void> deleteGrievance(@PathVariable UUID grievanceId) throws Exception {
        log.info("Handling request to delete grievance with ID: {}", grievanceId);
        grievanceService.deleteGrievance(grievanceId);
        log.info("Grievance {} deleted successfully", grievanceId);
        return ResponseEntity.noContent().build();
    }




}
