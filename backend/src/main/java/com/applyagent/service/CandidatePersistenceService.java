package com.applyagent.service;

import com.applyagent.entity.CandidateEntity;
import com.applyagent.entity.ProjectEntity;
import com.applyagent.model.CandidateProfile;
import com.applyagent.model.Project;
import com.applyagent.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidatePersistenceService {

    @Autowired
    private CandidateRepository candidateRepository;

    /**
     * Save a candidate to the database. Accepts data from the frontend form.
     */
    @Transactional
    public CandidateEntity saveCandidate(CandidateEntity entity) {
        return candidateRepository.save(entity);
    }

    /**
     * Save candidate from the frontend DTO-like map with jobDescription.
     */
    @Transactional
    public CandidateEntity saveCandidateFromRequest(String name, String email, String phone,
                                                     String education, String skills,
                                                     String experience, String certifications,
                                                     String jobDescription,
                                                     List<ProjectEntity> projects) {
        CandidateEntity entity = new CandidateEntity();
        entity.setName(name);
        entity.setEmail(email);
        entity.setPhone(phone);
        entity.setEducation(education);
        entity.setSkills(skills);
        entity.setExperience(experience);
        entity.setCertifications(certifications);
        entity.setJobDescription(jobDescription);

        if (projects != null) {
            for (ProjectEntity p : projects) {
                entity.addProject(p);
            }
        }

        return candidateRepository.save(entity);
    }

    /**
     * Get all candidates, most recently updated first.
     */
    public List<CandidateEntity> getAllCandidates() {
        return candidateRepository.findAllByOrderByUpdatedAtDesc();
    }

    /**
     * Get a candidate by ID.
     */
    public Optional<CandidateEntity> getCandidateById(Long id) {
        return candidateRepository.findById(id);
    }

    /**
     * Update an existing candidate.
     */
    @Transactional
    public CandidateEntity updateCandidate(Long id, CandidateEntity updated) {
        CandidateEntity existing = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + id));

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setEducation(updated.getEducation());
        existing.setSkills(updated.getSkills());
        existing.setExperience(updated.getExperience());
        existing.setCertifications(updated.getCertifications());
        existing.setJobDescription(updated.getJobDescription());

        // Replace projects
        existing.clearProjects();
        if (updated.getProjects() != null) {
            for (ProjectEntity p : updated.getProjects()) {
                existing.addProject(p);
            }
        }

        return candidateRepository.save(existing);
    }

    /**
     * Delete a candidate by ID.
     */
    @Transactional
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }

    /**
     * Convert a CandidateEntity to the existing CandidateProfile model
     * (used by the agent workflow).
     */
    public CandidateProfile toCandidateProfile(CandidateEntity entity) {
        CandidateProfile profile = new CandidateProfile();
        profile.setName(entity.getName());
        profile.setEducation(entity.getEducation());

        // Convert comma-separated skills to List
        if (entity.getSkills() != null && !entity.getSkills().isBlank()) {
            profile.setSkills(
                Arrays.stream(entity.getSkills().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList())
            );
        }

        // Convert ProjectEntity list to Project model list
        List<Project> projects = new ArrayList<>();
        if (entity.getProjects() != null) {
            for (ProjectEntity pe : entity.getProjects()) {
                List<String> techs = new ArrayList<>();
                if (pe.getTechnologies() != null && !pe.getTechnologies().isBlank()) {
                    techs = Arrays.stream(pe.getTechnologies().split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toList());
                }
                projects.add(new Project(pe.getName(), pe.getDescription(), techs));
            }
        }
        profile.setProjects(projects);

        // Experiences and certifications — store as empty for now
        // (the existing model uses lists; we store raw text in the entity)
        profile.setExperiences(new ArrayList<>());
        profile.setCertifications(new ArrayList<>());

        return profile;
    }
}
