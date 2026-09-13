package com.applyagent.service;

import com.applyagent.model.ResumeContent;
import com.applyagent.model.EvaluationResult;
import com.applyagent.model.MatchResult;
import org.springframework.stereotype.Service;

@Service
public class ResumeReviserService {

    public ResumeContent revise(ResumeContent resume, EvaluationResult eval, MatchResult matchResult) {
        // Deep copy not strictly needed for this mock, but we'll modify in place
        resume.setSummary(resume.getSummary() + " Highly motivated and eager to contribute to impactful projects.");
        
        // Final score bump
        eval.setFinalScore(Math.min(100, eval.getInitialScore() + 5));
        
        return resume;
    }
}
