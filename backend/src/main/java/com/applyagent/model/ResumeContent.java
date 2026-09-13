package com.applyagent.model;

import java.util.List;
import java.util.ArrayList;

public class ResumeContent {
    private String candidateName;
    private String education;
    private String summary;
    private List<ResumeSkill> skills = new ArrayList<>();
    private List<ResumeProject> projects = new ArrayList<>();
    private List<ResumeExperience> experiences = new ArrayList<>();
    private String targetRole;

    public ResumeContent() {}

    public ResumeContent(String candidateName, String education, String summary, List<ResumeSkill> skills, List<ResumeProject> projects, List<ResumeExperience> experiences, String targetRole) {
        this.candidateName = candidateName;
        this.education = education;
        this.summary = summary;
        this.skills = skills;
        this.projects = projects;
        this.experiences = experiences;
        this.targetRole = targetRole;
    }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public List<ResumeSkill> getSkills() { return skills; }
    public void setSkills(List<ResumeSkill> skills) { this.skills = skills; }
    public List<ResumeProject> getProjects() { return projects; }
    public void setProjects(List<ResumeProject> projects) { this.projects = projects; }
    public List<ResumeExperience> getExperiences() { return experiences; }
    public void setExperiences(List<ResumeExperience> experiences) { this.experiences = experiences; }
    public String getTargetRole() { return targetRole; }
    public void setTargetRole(String targetRole) { this.targetRole = targetRole; }
}
