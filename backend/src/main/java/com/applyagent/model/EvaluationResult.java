package com.applyagent.model;

import java.util.List;
import java.util.ArrayList;

public class EvaluationResult {
    private int atsScore;
    private int roleMatchScore;
    private int formattingScore;
    private int factualConsistencyScore;
    private int initialScore;
    private int finalScore;
    private List<String> weaknesses = new ArrayList<>();
    private List<String> revisionPlan = new ArrayList<>();
    private String overallAssessment;

    public EvaluationResult() {}

    public EvaluationResult(int atsScore, int roleMatchScore, int formattingScore, int factualConsistencyScore, int initialScore, int finalScore, List<String> weaknesses, List<String> revisionPlan, String overallAssessment) {
        this.atsScore = atsScore;
        this.roleMatchScore = roleMatchScore;
        this.formattingScore = formattingScore;
        this.factualConsistencyScore = factualConsistencyScore;
        this.initialScore = initialScore;
        this.finalScore = finalScore;
        this.weaknesses = weaknesses;
        this.revisionPlan = revisionPlan;
        this.overallAssessment = overallAssessment;
    }

    public int getAtsScore() { return atsScore; }
    public void setAtsScore(int atsScore) { this.atsScore = atsScore; }
    public int getRoleMatchScore() { return roleMatchScore; }
    public void setRoleMatchScore(int roleMatchScore) { this.roleMatchScore = roleMatchScore; }
    public int getFormattingScore() { return formattingScore; }
    public void setFormattingScore(int formattingScore) { this.formattingScore = formattingScore; }
    public int getFactualConsistencyScore() { return factualConsistencyScore; }
    public void setFactualConsistencyScore(int factualConsistencyScore) { this.factualConsistencyScore = factualConsistencyScore; }
    public int getInitialScore() { return initialScore; }
    public void setInitialScore(int initialScore) { this.initialScore = initialScore; }
    public int getFinalScore() { return finalScore; }
    public void setFinalScore(int finalScore) { this.finalScore = finalScore; }
    public List<String> getWeaknesses() { return weaknesses; }
    public void setWeaknesses(List<String> weaknesses) { this.weaknesses = weaknesses; }
    public List<String> getRevisionPlan() { return revisionPlan; }
    public void setRevisionPlan(List<String> revisionPlan) { this.revisionPlan = revisionPlan; }
    public String getOverallAssessment() { return overallAssessment; }
    public void setOverallAssessment(String overallAssessment) { this.overallAssessment = overallAssessment; }
}
