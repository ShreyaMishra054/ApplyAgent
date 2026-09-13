package com.applyagent.model;

import java.util.List;
import java.util.ArrayList;

public class Experience {
    private String role;
    private String company;
    private String duration;
    private String description;
    private List<String> technologies = new ArrayList<>();

    public Experience() {}

    public Experience(String role, String company, String duration, String description, List<String> technologies) {
        this.role = role;
        this.company = company;
        this.duration = duration;
        this.description = description;
        this.technologies = technologies;
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getTechnologies() { return technologies; }
    public void setTechnologies(List<String> technologies) { this.technologies = technologies; }
}
