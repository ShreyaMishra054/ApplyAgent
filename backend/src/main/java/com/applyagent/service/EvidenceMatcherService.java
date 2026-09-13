package com.applyagent.service;

import com.applyagent.model.CandidateProfile;
import com.applyagent.model.JobDescription;
import com.applyagent.model.MatchResult;
import com.applyagent.model.SkillMatch;
import com.applyagent.model.Project;
import com.applyagent.model.Experience;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class EvidenceMatcherService {

    public MatchResult match(JobDescription jd, CandidateProfile candidate) {
        List<SkillMatch> skillMatches = new ArrayList<>();
        List<String> supportedSkills = new ArrayList<>();
        List<String> unsupportedSkills = new ArrayList<>();
        
        List<String> allJdSkills = new ArrayList<>();
        if (jd.getRequiredSkills() != null) allJdSkills.addAll(jd.getRequiredSkills());
        if (jd.getPreferredSkills() != null) allJdSkills.addAll(jd.getPreferredSkills());
        
        for (String skill : allJdSkills) {
            String lowerSkill = skill.toLowerCase();
            boolean found = false;
            String source = "";
            String evidence = "";
            
            // Check skills
            if (candidate.getSkills() != null) {
                for (String cSkill : candidate.getSkills()) {
                    if (cSkill.toLowerCase().contains(lowerSkill) || lowerSkill.contains(cSkill.toLowerCase())) {
                        found = true;
                        source += "skill:" + cSkill + " ";
                        evidence += "Listed in skills. ";
                    }
                }
            }
            
            // Check projects
            if (candidate.getProjects() != null) {
                for (Project proj : candidate.getProjects()) {
                    if (proj.getTechnologies() != null) {
                        for (String tech : proj.getTechnologies()) {
                            if (tech.toLowerCase().contains(lowerSkill) || lowerSkill.contains(tech.toLowerCase())) {
                                found = true;
                                source += "project:" + proj.getName() + " ";
                                evidence += "Used in project " + proj.getName() + ". ";
                            }
                        }
                    }
                }
            }
            
            // Check experiences
            if (candidate.getExperiences() != null) {
                for (Experience exp : candidate.getExperiences()) {
                    if (exp.getTechnologies() != null) {
                        for (String tech : exp.getTechnologies()) {
                            if (tech.toLowerCase().contains(lowerSkill) || lowerSkill.contains(tech.toLowerCase())) {
                                found = true;
                                source += "experience:" + exp.getCompany() + " ";
                                evidence += "Used at " + exp.getCompany() + ". ";
                            }
                        }
                    }
                }
            }
            
            if (found) {
                skillMatches.add(new SkillMatch(skill, true, evidence.trim(), source.trim()));
                supportedSkills.add(skill);
            } else {
                skillMatches.add(new SkillMatch(skill, false, "Not found anywhere in profile", "None"));
                unsupportedSkills.add(skill);
            }
        }
        
        double matchPercentage = 0;
        if (!allJdSkills.isEmpty()) {
            matchPercentage = (supportedSkills.size() * 100.0) / allJdSkills.size();
        }
        
        return new MatchResult(skillMatches, supportedSkills, unsupportedSkills, matchPercentage);
    }
}
