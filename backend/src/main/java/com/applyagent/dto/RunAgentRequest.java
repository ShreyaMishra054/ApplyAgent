package com.applyagent.dto;

import com.applyagent.model.CandidateProfile;

public class RunAgentRequest {
    private String jobDescription;
    private CandidateProfile candidate;

    public RunAgentRequest() {}

    public RunAgentRequest(String jobDescription, CandidateProfile candidate) {
        this.jobDescription = jobDescription;
        this.candidate = candidate;
    }

    public String getJobDescription() { return jobDescription; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }
    public CandidateProfile getCandidate() { return candidate; }
    public void setCandidate(CandidateProfile candidate) { this.candidate = candidate; }
}
