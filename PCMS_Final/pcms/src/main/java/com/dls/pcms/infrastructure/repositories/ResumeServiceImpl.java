package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.ResumeService;
import com.dls.pcms.domain.models.Resume;
import com.dls.pcms.domain.repository.ResumeRepository;
import com.dls.pcms.domain.repository.StudentRepository;

import com.dls.pcms.domain.models.Student;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final StudentRepository studentRepository;

    public ResumeServiceImpl(ResumeRepository resumeRepository, StudentRepository studentRepository) {
        this.resumeRepository = resumeRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Resume saveResume(MultipartFile file, UUID studentId) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence " + fileName);
            }

            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new Exception("Student not found with Id: " + studentId));

            Resume resume = new Resume(fileName, file.getBytes(), student);
            return resumeRepository.save(resume);
        } catch (Exception e) {
            throw new Exception("Could not save resume: " + fileName, e);
        }
    }

    @Override
    public Resume getResume(UUID resumeId) throws Exception {
        return resumeRepository.findById(resumeId)
                .orElseThrow(() -> new Exception("Resume not found with Id: " + resumeId));
    }
    @Override
    public List<Resume> getAllResumes() {
       // log.info("Fetching all resumes");
        List<Resume> resumes = resumeRepository.findAll();
        //log.info("Found {} resumes", resumes.size());
        return resumes;
    }

    @Override
    public void deleteResume(UUID resumeId) throws Exception {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new Exception("Resume not found with Id: " + resumeId));
        resumeRepository.delete(resume);
    }
}
