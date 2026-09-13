package com.applyagent.model;

import java.util.List;
import java.util.ArrayList;

public class ChangeReport {
    private List<ChangeReportEntry> changes = new ArrayList<>();
    private List<String> rejectedUnsupportedClaims = new ArrayList<>();
    private String summary;

    public ChangeReport() {}

    public ChangeReport(List<ChangeReportEntry> changes, List<String> rejectedUnsupportedClaims, String summary) {
        this.changes = changes;
        this.rejectedUnsupportedClaims = rejectedUnsupportedClaims;
        this.summary = summary;
    }

    public List<ChangeReportEntry> getChanges() { return changes; }
    public void setChanges(List<ChangeReportEntry> changes) { this.changes = changes; }
    public List<String> getRejectedUnsupportedClaims() { return rejectedUnsupportedClaims; }
    public void setRejectedUnsupportedClaims(List<String> rejectedUnsupportedClaims) { this.rejectedUnsupportedClaims = rejectedUnsupportedClaims; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}
