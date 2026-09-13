package com.applyagent.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class AgentResult {
    private JobDescription parsedJd;
    private CandidateProfile candidate;
    private MatchResult matchResult;
    private ResumeContent initialResume;
    private EvaluationResult evaluation;
    private ResumeContent revisedResume;
    private VerificationResult verification;
    private ChangeReport changeReport;
    private List<WorkflowStatus> workflowSteps = new ArrayList<>();
    private String pdfFileName;
    private Map<String, Object> companyResearch = new HashMap<>();

    public AgentResult() {}

    public AgentResult(JobDescription parsedJd, CandidateProfile candidate, MatchResult matchResult, ResumeContent initialResume, EvaluationResult evaluation, ResumeContent revisedResume, VerificationResult verification, ChangeReport changeReport, List<WorkflowStatus> workflowSteps, String pdfFileName, Map<String, Object> companyResearch) {
        this.parsedJd = parsedJd;
        this.candidate = candidate;
        this.matchResult = matchResult;
        this.initialResume = initialResume;
        this.evaluation = evaluation;
        this.revisedResume = revisedResume;
        this.verification = verification;
        this.changeReport = changeReport;
        this.workflowSteps = workflowSteps;
        this.pdfFileName = pdfFileName;
        this.companyResearch = companyResearch;
    }

    public JobDescription getParsedJd() { return parsedJd; }
    public void setParsedJd(JobDescription parsedJd) { this.parsedJd = parsedJd; }
    public CandidateProfile getCandidate() { return candidate; }
    public void setCandidate(CandidateProfile candidate) { this.candidate = candidate; }
    public MatchResult getMatchResult() { return matchResult; }
    public void setMatchResult(MatchResult matchResult) { this.matchResult = matchResult; }
    public ResumeContent getInitialResume() { return initialResume; }
    public void setInitialResume(ResumeContent initialResume) { this.initialResume = initialResume; }
    public EvaluationResult getEvaluation() { return evaluation; }
    public void setEvaluation(EvaluationResult evaluation) { this.evaluation = evaluation; }
    public ResumeContent getRevisedResume() { return revisedResume; }
    public void setRevisedResume(ResumeContent revisedResume) { this.revisedResume = revisedResume; }
    public VerificationResult getVerification() { return verification; }
    public void setVerification(VerificationResult verification) { this.verification = verification; }
    public ChangeReport getChangeReport() { return changeReport; }
    public void setChangeReport(ChangeReport changeReport) { this.changeReport = changeReport; }
    public List<WorkflowStatus> getWorkflowSteps() { return workflowSteps; }
    public void setWorkflowSteps(List<WorkflowStatus> workflowSteps) { this.workflowSteps = workflowSteps; }
    public String getPdfFileName() { return pdfFileName; }
    public void setPdfFileName(String pdfFileName) { this.pdfFileName = pdfFileName; }
    public Map<String, Object> getCompanyResearch() { return companyResearch; }
    public void setCompanyResearch(Map<String, Object> companyResearch) { this.companyResearch = companyResearch; }
}
