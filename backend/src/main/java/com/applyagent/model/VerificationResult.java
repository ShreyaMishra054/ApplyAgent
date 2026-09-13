package com.applyagent.model;

import java.util.List;
import java.util.ArrayList;

public class VerificationResult {
    private boolean allFactsVerified;
    private List<VerificationItem> items = new ArrayList<>();
    private List<String> rejectedClaims = new ArrayList<>();

    public VerificationResult() {}

    public VerificationResult(boolean allFactsVerified, List<VerificationItem> items, List<String> rejectedClaims) {
        this.allFactsVerified = allFactsVerified;
        this.items = items;
        this.rejectedClaims = rejectedClaims;
    }

    public boolean isAllFactsVerified() { return allFactsVerified; }
    public void setAllFactsVerified(boolean allFactsVerified) { this.allFactsVerified = allFactsVerified; }
    public List<VerificationItem> getItems() { return items; }
    public void setItems(List<VerificationItem> items) { this.items = items; }
    public List<String> getRejectedClaims() { return rejectedClaims; }
    public void setRejectedClaims(List<String> rejectedClaims) { this.rejectedClaims = rejectedClaims; }
}
