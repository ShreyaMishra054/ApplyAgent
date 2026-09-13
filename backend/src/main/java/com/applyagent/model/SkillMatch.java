package com.applyagent.model;

public class SkillMatch {
    private String skill;
    private boolean supported;
    private String evidence;
    private String source;

    public SkillMatch() {}

    public SkillMatch(String skill, boolean supported, String evidence, String source) {
        this.skill = skill;
        this.supported = supported;
        this.evidence = evidence;
        this.source = source;
    }

    public String getSkill() { return skill; }
    public void setSkill(String skill) { this.skill = skill; }
    public boolean isSupported() { return supported; }
    public void setSupported(boolean supported) { this.supported = supported; }
    public String getEvidence() { return evidence; }
    public void setEvidence(String evidence) { this.evidence = evidence; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
