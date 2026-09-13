package com.applyagent.model;

import java.util.List;
import java.util.ArrayList;

public class CandidateProfile {
    private String name;
    private String education;
    private List<String> skills = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    private List<Experience> experiences = new ArrayList<>();
    private List<String> certifications = new ArrayList<>();

    public CandidateProfile() {}

    public CandidateProfile(String name, String education, List<String> skills, List<Project> projects, List<Experience> experiences, List<String> certifications) {
        this.name = name;
        this.education = education;
        this.skills = skills;
        this.projects = projects;
        this.experiences = experiences;
        this.certifications = certifications;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
    public List<Project> getProjects() { return projects; }
    public void setProjects(List<Project> projects) { this.projects = projects; }
    public List<Experience> getExperiences() { return experiences; }
    public void setExperiences(List<Experience> experiences) { this.experiences = experiences; }
    public List<String> getCertifications() { return certifications; }
    public void setCertifications(List<String> certifications) { this.certifications = certifications; }
}
