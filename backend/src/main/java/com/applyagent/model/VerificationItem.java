package com.applyagent.model;

public class VerificationItem {
    private String claim;
    private boolean verified;
    private String evidence;
    private String status;

    public VerificationItem() {}

    public VerificationItem(String claim, boolean verified, String evidence, String status) {
        this.claim = claim;
        this.verified = verified;
        this.evidence = evidence;
        this.status = status;
    }

    public String getClaim() { return claim; }
    public void setClaim(String claim) { this.claim = claim; }
    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
    public String getEvidence() { return evidence; }
    public void setEvidence(String evidence) { this.evidence = evidence; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
