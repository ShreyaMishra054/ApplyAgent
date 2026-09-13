package com.applyagent.model;

public class ResumeExperience {
    private String role;
    private String company;
    private String duration;
    private String description;

    public ResumeExperience() {}

    public ResumeExperience(String role, String company, String duration, String description) {
        this.role = role;
        this.company = company;
        this.duration = duration;
        this.description = description;
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
