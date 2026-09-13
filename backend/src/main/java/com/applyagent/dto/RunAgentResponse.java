package com.applyagent.dto;

import com.applyagent.model.AgentResult;

public class RunAgentResponse {
    private boolean success;
    private AgentResult result;
    private String message;

    public RunAgentResponse() {}

    public RunAgentResponse(boolean success, AgentResult result, String message) {
        this.success = success;
        this.result = result;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public AgentResult getResult() { return result; }
    public void setResult(AgentResult result) { this.result = result; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
