package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.GrievanceService;
import com.dls.pcms.domain.models.Grievance;
import com.dls.pcms.domain.models.JobApplication;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.repository.GrievanceRepository;

import com.dls.pcms.domain.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GrievanceServiceImpl implements GrievanceService {

    private final GrievanceRepository grievanceRepository;
    private final StudentRepository studentRepository;

    public GrievanceServiceImpl(GrievanceRepository grievanceRepository, StudentRepository studentRepository) {
        this.grievanceRepository = grievanceRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Grievance saveGrievance(MultipartFile file, UUID studentId, String description, String subject) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence " + fileName);
            }

            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new Exception("Student not found with Id: " + studentId));

            Grievance grievance = new Grievance(fileName, file.getBytes(), student);
            grievance.setDescription(description);
            grievance.setSubject(subject);
            return grievanceRepository.save(grievance);
        } catch (Exception e) {
            throw new Exception("Could not save grievance: " + fileName, e);
        }
    }

    @Override
    public Grievance getGrievance(UUID grievanceId) throws Exception {
        return grievanceRepository.findById(grievanceId)
                .orElseThrow(() -> new Exception("Grievance not found with Id: " + grievanceId));
    }

    @Override
    public List<Grievance> getAllGrievances() {
        List<Grievance> grievances = grievanceRepository.findAll();
        return grievances;
    }

    @Override
    public void deleteGrievance(UUID grievanceId) throws Exception {
        Grievance grievance = grievanceRepository.findById(grievanceId)
                .orElseThrow(() -> new Exception("Grievance not found with Id: " + grievanceId));
        grievanceRepository.delete(grievance);
    }

    @Override
    public Page<Grievance> getAllGrievancesPage(Pageable pageable){
        Page<Grievance> grievances = grievanceRepository.findAll(pageable);
        return grievances;
    }

    public Grievance findById(UUID grievanceId) {
        Optional<Grievance> grievance = grievanceRepository.findById(grievanceId);
        return grievance.orElse(null);
    }
}
