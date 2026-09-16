package com.applyagent.controller;

import com.applyagent.entity.CandidateEntity;
import com.applyagent.entity.ProjectEntity;
import com.applyagent.service.CandidatePersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidatePersistenceService persistenceService;

    /**
     * GET /api/candidates — retrieve all saved candidates
     */
    @GetMapping
    public ResponseEntity<List<CandidateEntity>> getAllCandidates() {
        return ResponseEntity.ok(persistenceService.getAllCandidates());
    }

    /**
     * GET /api/candidates/{id} — retrieve a specific candidate
     */
    @GetMapping("/{id}")
    public ResponseEntity<CandidateEntity> getCandidateById(@PathVariable Long id) {
        return persistenceService.getCandidateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/candidates — save a new candidate
     * Accepts JSON body with: name, email, phone, education, skills, experience,
     * certifications, jobDescription, projects[{name, description, technologies}]
     */
    @PostMapping
    public ResponseEntity<CandidateEntity> saveCandidate(@RequestBody Map<String, Object> body) {
        CandidateEntity entity = new CandidateEntity();
        entity.setName(getStr(body, "name"));
        entity.setEmail(getStr(body, "email"));
        entity.setPhone(getStr(body, "phone"));
        entity.setEducation(getStr(body, "education"));
        entity.setSkills(getStr(body, "skills"));
        entity.setExperience(getStr(body, "experience"));
        entity.setCertifications(getStr(body, "certifications"));
        entity.setJobDescription(getStr(body, "jobDescription"));

        // Parse projects
        Object projectsObj = body.get("projects");
        if (projectsObj instanceof List<?>) {
            for (Object pObj : (List<?>) projectsObj) {
                if (pObj instanceof Map<?, ?>) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> pMap = (Map<String, Object>) pObj;
                    String pName = pMap.get("name") != null ? pMap.get("name").toString() : "";
                    String pDesc = pMap.get("description") != null ? pMap.get("description").toString() : "";
                    String pTech = pMap.get("technologies") != null ? pMap.get("technologies").toString() : "";
                    entity.addProject(new ProjectEntity(pName, pDesc, pTech));
                }
            }
        }

        CandidateEntity saved = persistenceService.saveCandidate(entity);
        return ResponseEntity.ok(saved);
    }

    /**
     * PUT /api/candidates/{id} — update an existing candidate
     */
    @PutMapping("/{id}")
    public ResponseEntity<CandidateEntity> updateCandidate(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        CandidateEntity updated = new CandidateEntity();
        updated.setName(getStr(body, "name"));
        updated.setEmail(getStr(body, "email"));
        updated.setPhone(getStr(body, "phone"));
        updated.setEducation(getStr(body, "education"));
        updated.setSkills(getStr(body, "skills"));
        updated.setExperience(getStr(body, "experience"));
        updated.setCertifications(getStr(body, "certifications"));
        updated.setJobDescription(getStr(body, "jobDescription"));

        Object projectsObj = body.get("projects");
        List<ProjectEntity> projects = new ArrayList<>();
        if (projectsObj instanceof List<?>) {
            for (Object pObj : (List<?>) projectsObj) {
                if (pObj instanceof Map<?, ?>) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> pMap = (Map<String, Object>) pObj;
                    String pName = pMap.get("name") != null ? pMap.get("name").toString() : "";
                    String pDesc = pMap.get("description") != null ? pMap.get("description").toString() : "";
                    String pTech = pMap.get("technologies") != null ? pMap.get("technologies").toString() : "";
                    projects.add(new ProjectEntity(pName, pDesc, pTech));
                }
            }
        }
        updated.setProjects(projects);

        try {
            CandidateEntity result = persistenceService.updateCandidate(id, updated);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/candidates/{id} — delete a candidate
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        try {
            persistenceService.deleteCandidate(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String getStr(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null ? val.toString() : null;
    }
}
