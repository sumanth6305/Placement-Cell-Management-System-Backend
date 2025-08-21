package com.dls.pcms.application.services;

import  com.dls.pcms.domain.models.JobPosting;
import com.dls.pcms.domain.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentService {

    Student createStudent(Student student);

    Optional<Student> getStudentById(UUID studentId);

    List<Student> getAllStudents();
    Optional<Student> getSkillsById(UUID studentId);
    Student updateStudent(UUID studentId, Student studentDetails);
    Student updateSkillsByStudentId(UUID studentId, Student updatedSkills);
    Student findById(UUID studentId);

    void deleteStudent(UUID studentId);
  //  Page<JobPosting> getAvailableJobOpeningsForStudent(UUID studentId, Pageable pageable);
    // Add more custom methods as needed
}
