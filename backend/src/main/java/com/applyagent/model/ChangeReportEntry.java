package com.applyagent.model;

public class ChangeReportEntry {
    private String change;
    private String reason;
    private String supportingEvidence;
    private String verificationResult;

    public ChangeReportEntry() {}

    public ChangeReportEntry(String change, String reason, String supportingEvidence, String verificationResult) {
        this.change = change;
        this.reason = reason;
        this.supportingEvidence = supportingEvidence;
        this.verificationResult = verificationResult;
    }

    public String getChange() { return change; }
    public void setChange(String change) { this.change = change; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getSupportingEvidence() { return supportingEvidence; }
    public void setSupportingEvidence(String supportingEvidence) { this.supportingEvidence = supportingEvidence; }
    public String getVerificationResult() { return verificationResult; }
    public void setVerificationResult(String verificationResult) { this.verificationResult = verificationResult; }
}
