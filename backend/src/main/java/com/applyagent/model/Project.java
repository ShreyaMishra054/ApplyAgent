package com.applyagent.model;

import java.util.List;
import java.util.ArrayList;

public class Project {
    private String name;
    private String description;
    private List<String> technologies = new ArrayList<>();

    public Project() {}

    public Project(String name, String description, List<String> technologies) {
        this.name = name;
        this.description = description;
        this.technologies = technologies;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getTechnologies() { return technologies; }
    public void setTechnologies(List<String> technologies) { this.technologies = technologies; }
}
