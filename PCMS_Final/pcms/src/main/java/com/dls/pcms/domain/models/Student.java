package com.dls.pcms.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.constraints.URL;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@Table(name = "Stu
@Setter
@AllArgsConstructor
@NoArgsConstructordent")
public class Student {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "student_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID studentId;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "date_of_birth")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be 'Male', 'Female', or 'Other'")
    private String gender;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "phone_number")
   @Pattern(regexp = "^$|[0-9]{10,15}", message = "Phone number must be a valid number with 10 to 15 digits")
    private String phoneNumber;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city")
    @NotBlank(message = "City is mandatory")
    private String city;

    @Column(name = "state")
    @NotBlank(message = "State is mandatory")
    private String state;

    @Column(name = "zip_code")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Zip code must be a valid format")
    private String zipCode;

    @Column(name = "country")
    @NotBlank(message = "Country is mandatory")
    private String country;

    @Column(name = "academic_details", columnDefinition = "TEXT")
    private String academicDetails; // Stored as JSON string

    @JsonProperty("academic_details")
    public JsonNode getAcademicDetailsAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.academicDetails);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("academic_details")
    public void setAcademicDetailsAsJsonNode(JsonNode jsonNode) {
        this.academicDetails = jsonNode.toString();
    }

    @Column(name = "linkedin_profile")
    @URL(message = "LinkedIn profile must be a valid URL")
    private String linkedinProfile;

    @Column(name = "github_profile")
    @URL(message = "GitHub profile must be a valid URL")
    private String githubProfile;

    @Column(name = "personal_website")
    @URL(message = "Personal website must be a valid URL")
    private String personalWebsite;

    @Column(name = "skills", columnDefinition = "TEXT")
    private String skills; // Stored as JSON string

    @JsonProperty("skills")
    public JsonNode getSkillsAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.skills);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("skills")
    public void setSkillsAsJsonNode(JsonNode jsonNode) {
        this.skills = jsonNode.toString();
    }

    @Column(name = "projects", columnDefinition = "TEXT")
    private String projects; // Stored as JSON string

    @JsonProperty("projects")
    public JsonNode getProjectsAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.projects);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("projects")
    public void setProjectsAsJsonNode(JsonNode jsonNode) {
        this.projects = jsonNode.toString();
    }

    @Column(name = "certifications", columnDefinition = "TEXT")
    private String certifications; // Stored as JSON string

    @JsonProperty("certifications")
    public JsonNode getCertificationsAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.certifications);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("certifications")
    public void setCertificationsAsJsonNode(JsonNode jsonNode) {
        this.certifications = jsonNode.toString();
    }

    @Column(name = "languages", columnDefinition = "TEXT")
    private String languages; // Stored as JSON string

    @JsonProperty("languages")
    public JsonNode getLanguagesAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.languages);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("languages")
    public void setLanguagesAsJsonNode(JsonNode jsonNode) {
        this.languages = jsonNode.toString();
    }

    @Column(name = "professional_experience", columnDefinition = "TEXT")
    private String professionalExperience; // Stored as JSON string

    @JsonProperty("professional_experience")
    public JsonNode getProfessionalExperienceAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.professionalExperience);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("professional_experience")
    public void setProfessionalExperienceAsJsonNode(JsonNode jsonNode) {
        this.professionalExperience = jsonNode.toString();
    }

    @Column(name = "internships", columnDefinition = "TEXT")
    private String internships; // Stored as JSON string

    @JsonProperty("internships")
    public JsonNode getInternshipsAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.internships);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("internships")
    public void setInternshipsAsJsonNode(JsonNode jsonNode) {
        this.internships = jsonNode.toString();
    }

    @Column(name = "awards_and_honors", columnDefinition = "TEXT")
    private String awardsAndHonors; // Stored as JSON string

    @JsonProperty("awards_and_honors")
    public JsonNode getAwardsAndHonorsAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.awardsAndHonors);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("awards_and_honors")
    public void setAwardsAndHonorsAsJsonNode(JsonNode jsonNode) {
        this.awardsAndHonors = jsonNode.toString();
    }

    @Column(name = "interests", columnDefinition = "TEXT")
    private String interests; // Stored as JSON string

    @JsonProperty("interests")
    public JsonNode getInterestsAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.interests);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("interests")
    public void setInterestsAsJsonNode(JsonNode jsonNode) {
        this.interests = jsonNode.toString();
    }

    @Column(name = "student_references", columnDefinition = "TEXT")
    private String studentReferences; // Renamed from references

    @JsonProperty("student_references")Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city")
    @NotBlank(message = "City is mandatory")
    private String city;

    @Column(name = "state")
    @NotBlank(message = "State is mandatory")
    private String state;
    Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city")
    @NotBlank(message = "City is mandatory")
    private String city;

    @Column(name = "state")
    @NotBlank(message = "State is mandatory")
    private String state;

    public JsonNode getStudentReferencesAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.studentReferences);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("student_references")
    public void setStudentReferencesAsJsonNode(JsonNode jsonNode) {
        this.studentReferences = jsonNode.toString();
    }

    @Column(name = "additional_information", columnDefinition = "TEXT")
    private String additionalInformation; // Stored as JSON string

    @JsonProperty("additional_information")
    public JsonNode getAdditionalInformationAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.additionalInformation);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("additional_information")
    public void setAdditionalInformationAsJsonNode(JsonNode jsonNode) {
        this.additionalInformation = jsonNode.toString();
    }

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.studentId == null) {
            this.studentId = UUID.randomUUID();
        }
    }

   
}
