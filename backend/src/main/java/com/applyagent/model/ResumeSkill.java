package com.applyagent.model;

public class ResumeSkill {
    private String skill;
    private String evidence;

    public ResumeSkill() {}

    public ResumeSkill(String skill, String evidence) {
        this.skill = skill;
        this.evidence = evidence;
    }

    public String getSkill() { return skill; }
    public void setSkill(String skill) { this.skill = skill; }
    public String getEvidence() { return evidence; }
    public void setEvidence(String evidence) { this.evidence = evidence; }
}
