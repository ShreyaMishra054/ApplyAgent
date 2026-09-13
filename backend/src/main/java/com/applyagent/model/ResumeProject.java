package com.applyagent.model;

import java.util.List;
import java.util.ArrayList;

public class ResumeProject {
    private String name;
    private String description;
    private List<String> technologies = new ArrayList<>();
    private String relevance;

    public ResumeProject() {}

    public ResumeProject(String name, String description, List<String> technologies, String relevance) {
        this.name = name;
        this.description = description;
        this.technologies = technologies;
        this.relevance = relevance;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getTechnologies() { return technologies; }
    public void setTechnologies(List<String> technologies) { this.technologies = technologies; }
    public String getRelevance() { return relevance; }
    public void setRelevance(String relevance) { this.relevance = relevance; }
}
