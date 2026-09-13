package com.applyagent.model;

import java.util.List;
import java.util.ArrayList;

public class JobDescription {
    private String rawText;
    private String title;
    private String company;
    private List<String> requiredSkills = new ArrayList<>();
    private List<String> preferredSkills = new ArrayList<>();
    private List<String> responsibilities = new ArrayList<>();
    private String experienceLevel;

    public JobDescription() {}

    public JobDescription(String rawText, String title, String company, List<String> requiredSkills, List<String> preferredSkills, List<String> responsibilities, String experienceLevel) {
        this.rawText = rawText;
        this.title = title;
        this.company = company;
        this.requiredSkills = requiredSkills;
        this.preferredSkills = preferredSkills;
        this.responsibilities = responsibilities;
        this.experienceLevel = experienceLevel;
    }

    public String getRawText() { return rawText; }
    public void setRawText(String rawText) { this.rawText = rawText; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public List<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<String> requiredSkills) { this.requiredSkills = requiredSkills; }
    public List<String> getPreferredSkills() { return preferredSkills; }
    public void setPreferredSkills(List<String> preferredSkills) { this.preferredSkills = preferredSkills; }
    public List<String> getResponsibilities() { return responsibilities; }
    public void setResponsibilities(List<String> responsibilities) { this.responsibilities = responsibilities; }
    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }
}
