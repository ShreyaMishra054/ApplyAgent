package com.applyagent.model;

import java.util.List;
import java.util.ArrayList;

public class MatchResult {
    private List<SkillMatch> skillMatches = new ArrayList<>();
    private List<String> supportedSkills = new ArrayList<>();
    private List<String> unsupportedSkills = new ArrayList<>();
    private double matchPercentage;

    public MatchResult() {}

    public MatchResult(List<SkillMatch> skillMatches, List<String> supportedSkills, List<String> unsupportedSkills, double matchPercentage) {
        this.skillMatches = skillMatches;
        this.supportedSkills = supportedSkills;
        this.unsupportedSkills = unsupportedSkills;
        this.matchPercentage = matchPercentage;
    }

    public List<SkillMatch> getSkillMatches() { return skillMatches; }
    public void setSkillMatches(List<SkillMatch> skillMatches) { this.skillMatches = skillMatches; }
    public List<String> getSupportedSkills() { return supportedSkills; }
    public void setSupportedSkills(List<String> supportedSkills) { this.supportedSkills = supportedSkills; }
    public List<String> getUnsupportedSkills() { return unsupportedSkills; }
    public void setUnsupportedSkills(List<String> unsupportedSkills) { this.unsupportedSkills = unsupportedSkills; }
    public double getMatchPercentage() { return matchPercentage; }
    public void setMatchPercentage(double matchPercentage) { this.matchPercentage = matchPercentage; }
}
