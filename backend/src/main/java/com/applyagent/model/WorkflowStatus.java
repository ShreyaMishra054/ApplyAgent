package com.applyagent.model;

public class WorkflowStatus {
    private String step;
    private boolean completed;
    private String status;
    private String message;
    private long timestamp;

    public WorkflowStatus() {}

    public WorkflowStatus(String step, boolean completed, String status, String message, long timestamp) {
        this.step = step;
        this.completed = completed;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getStep() { return step; }
    public void setStep(String step) { this.step = step; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
