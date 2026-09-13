package com.applyagent.service;

import com.applyagent.model.CandidateProfile;
import com.applyagent.model.JobDescription;
import com.applyagent.model.MatchResult;
import com.applyagent.model.ResumeContent;
import com.applyagent.model.ResumeSkill;
import com.applyagent.model.ResumeProject;
import com.applyagent.model.ResumeExperience;
import com.applyagent.model.Project;
import com.applyagent.model.Experience;
import com.applyagent.model.SkillMatch;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class ResumeGeneratorService {

    public ResumeContent generate(MatchResult matchResult, CandidateProfile candidate, JobDescription jd) {
        ResumeContent resume = new ResumeContent();
        resume.setCandidateName(candidate.getName());
        resume.setEducation(candidate.getEducation());
        resume.setTargetRole(jd.getTitle());
        
        String summary = "Experienced software professional with strong skills in " + String.join(", ", matchResult.getSupportedSkills()) + ".";
        resume.setSummary(summary);
        
        List<ResumeSkill> rSkills = new ArrayList<>();
        for (SkillMatch sm : matchResult.getSkillMatches()) {
            if (sm.isSupported()) {
                rSkills.add(new ResumeSkill(sm.getSkill(), sm.getEvidence()));
            }
        }
        resume.setSkills(rSkills);
        
        List<ResumeProject> rProjects = new ArrayList<>();
        if (candidate.getProjects() != null) {
            for (Project p : candidate.getProjects()) {
                rProjects.add(new ResumeProject(p.getName(), p.getDescription(), p.getTechnologies(), "Relevant to " + jd.getTitle()));
            }
        }
        resume.setProjects(rProjects);
        
        List<ResumeExperience> rExps = new ArrayList<>();
        if (candidate.getExperiences() != null) {
            for (Experience e : candidate.getExperiences()) {
                rExps.add(new ResumeExperience(e.getRole(), e.getCompany(), e.getDuration(), e.getDescription()));
            }
        }
        resume.setExperiences(rExps);
        
        return resume;
    }
}
