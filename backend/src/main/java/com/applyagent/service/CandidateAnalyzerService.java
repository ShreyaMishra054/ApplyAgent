package com.applyagent.service;

import com.applyagent.model.CandidateProfile;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;

@Service
public class CandidateAnalyzerService {

    public Map<String, Object> analyze(CandidateProfile candidate) {
        Map<String, Object> analysis = new HashMap<>();
        analysis.put("totalSkills", candidate.getSkills() != null ? candidate.getSkills().size() : 0);
        analysis.put("totalProjects", candidate.getProjects() != null ? candidate.getProjects().size() : 0);
        analysis.put("totalExperiences", candidate.getExperiences() != null ? candidate.getExperiences().size() : 0);
        analysis.put("education", candidate.getEducation());
        return analysis;
    }
}
