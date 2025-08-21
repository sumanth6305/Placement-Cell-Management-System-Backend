package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.StudentService;
import com.dls.pcms.domain.models.JobPosting;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student createStudent(Student student) {
        log.info("Creating new student with name: {}", student.getName());
        Student savedStudent = studentRepository.save(student);
        log.info("Student created successfully with ID: {}", savedStudent.getStudentId());
        return savedStudent;
    }

    @Override
    public Optional<Student> getStudentById(UUID studentId) {
        log.info("Fetching student by ID: {}", studentId);
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            log.info("Student found with ID: {}", studentId);
        } else {
            log.info("No student found with ID: {}", studentId);
        }
        return student;
    }
    @Override
    public Optional<Student> getSkillsById(UUID studentId) {
        log.info("Fetching skills for student by ID: {}", studentId);

        // Assuming there is a method in the studentRepository to get the student and their skills
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isPresent() && student.get().getSkills() != null) {
            log.info("Skills found for student with ID: {}", studentId);
        } else if (student.isPresent()) {
            log.info("No skills found for student with ID: {}", studentId);
        } else {
            log.info("No student found with ID: {}", studentId);
        }

        return student;
    }


    @Override
    public List<Student> getAllStudents() {
        log.info("Fetching all students");
        List<Student> students = studentRepository.findAll();
        log.info("Found {} students", students.size());
        return students;
    }


    @Override
    public Student updateStudent(UUID studentId, Student studentDetails) {
        log.info("Updating student with ID: {}", studentId);
        Optional<Student> existingStudentOpt = studentRepository.findById(studentId);

        if (existingStudentOpt.isPresent()) {
            Student existingStudent = existingStudentOpt.get();
            existingStudent.setName(studentDetails.getName());
            existingStudent.setDateOfBirth(studentDetails.getDateOfBirth());
            existingStudent.setGender(studentDetails.getGender());
            existingStudent.setEmail(studentDetails.getEmail());
            existingStudent.setPhoneNumber(studentDetails.getPhoneNumber());
            existingStudent.setAddressLine1(studentDetails.getAddressLine1());
            existingStudent.setAddressLine2(studentDetails.getAddressLine2());
            existingStudent.setCity(studentDetails.getCity());
            existingStudent.setState(studentDetails.getState());
            existingStudent.setZipCode(studentDetails.getZipCode());
            existingStudent.setCountry(studentDetails.getCountry());
            existingStudent.setAcademicDetails(studentDetails.getAcademicDetails());
            existingStudent.setLinkedinProfile(studentDetails.getLinkedinProfile());
            existingStudent.setGithubProfile(studentDetails.getGithubProfile());
            existingStudent.setPersonalWebsite(studentDetails.getPersonalWebsite());
            existingStudent.setSkills(studentDetails.getSkills());
            existingStudent.setProjects(studentDetails.getProjects());
            existingStudent.setCertifications(studentDetails.getCertifications());
            existingStudent.setLanguages(studentDetails.getLanguages());
            existingStudent.setProfessionalExperience(studentDetails.getProfessionalExperience());
            existingStudent.setInternships(studentDetails.getInternships());
            existingStudent.setAwardsAndHonors(studentDetails.getAwardsAndHonors());
            existingStudent.setInterests(studentDetails.getInterests());
            existingStudent.setStudentReferences(studentDetails.getStudentReferences());
            existingStudent.setAdditionalInformation(studentDetails.getAdditionalInformation());
            existingStudent.setUpdatedAt(studentDetails.getUpdatedAt());

            Student updatedStudent = studentRepository.save(existingStudent);
            log.info("Student updated successfully with ID: {}", studentId);
            return updatedStudent;
        } else {
            log.error("No student found with ID: {}", studentId);
            return null;
        }
    }

    @Override
    public void deleteStudent(UUID studentId) {
        log.info("Deleting student with ID: {}", studentId);
        studentRepository.deleteById(studentId);
        log.info("Student deleted successfully with ID: {}", studentId);
    }


    public Student findById(UUID studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        return student.orElse(null);
    }
    @Override
    public Student updateSkillsByStudentId(UUID studentId, Student updatedSkills) {
        log.info("Updating skills for student ID: {}", studentId);

        // Fetch the student by ID
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (studentOptional.isPresent()) {
            Student existingStudent = studentOptional.get();

            // Assuming the Student entity has a setSkills() method to update skills
            existingStudent.setSkills(updatedSkills.getSkills());

            // Save the updated student with new skills
            Student updatedStudent = studentRepository.save(existingStudent);

            log.info("Skills updated successfully for student ID: {}", studentId);
            return updatedStudent;
        } else {
            log.error("Student with ID: {} not found. Update failed.", studentId);
            throw new EntityNotFoundException("Student with ID " + studentId + " not found");
        }
    }


}
