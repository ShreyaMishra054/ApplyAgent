package com.applyagent.service;

import com.applyagent.model.ResumeContent;
import com.applyagent.model.EvaluationResult;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.ArrayList;

@Service
public class ResumeEvaluatorService {

    public EvaluationResult evaluate(ResumeContent resume) {
        EvaluationResult eval = new EvaluationResult();
        eval.setAtsScore(85);
        eval.setRoleMatchScore(80);
        eval.setFormattingScore(95);
        eval.setFactualConsistencyScore(100);
        
        int initialScore = (85 + 80 + 95 + 100) / 4;
        eval.setInitialScore(initialScore);
        eval.setFinalScore(initialScore);
        
        eval.setWeaknesses(new ArrayList<>(Arrays.asList("Summary is somewhat brief", "Projects could highlight impact more clearly")));
        eval.setRevisionPlan(new ArrayList<>(Arrays.asList("Expand professional summary", "Add quantitative metrics to projects if possible")));
        eval.setOverallAssessment("Strong candidate with verifiable skills.");
        
        return eval;
    }
}
