package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.JobPosting;
import com.dls.pcms.domain.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    List<Student> findByName(String name);

    List<Student> findByEmail(String email);

    List<Student> findByCity(String city);

    List<Student> findByState(String state);

    List<Student> findByCountry(String country);

    List<Student> findByAcademicDetailsContaining(String keyword);

    List<Student> findBySkillsContaining(String skill);

    List<Student> findByCertificationsContaining(String certification);


    default List<Student> findByNameOrEmail(@Param("name") String name, @Param("email") String email) {
        // Custom logic to find students by name or email
        List<Student> studentList = null;
        // Implement your custom logic here
        return studentList;
    }
}
