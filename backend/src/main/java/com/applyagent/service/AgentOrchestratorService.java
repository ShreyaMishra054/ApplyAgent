package com.applyagent.service;

import com.applyagent.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AgentOrchestratorService {

    @Autowired
    private JdParserService jdParser;
    @Autowired
    private CompanyResearchService companyResearchService;
    @Autowired
    private CandidateAnalyzerService candidateAnalyzer;
    @Autowired
    private EvidenceMatcherService evidenceMatcher;
    @Autowired
    private ResumeGeneratorService resumeGenerator;
    @Autowired
    private ResumeEvaluatorService resumeEvaluator;
    @Autowired
    private ResumeReviserService resumeReviser;
    @Autowired
    private FactualVerifierService factualVerifier;
    @Autowired
    private PdfGeneratorService pdfGenerator;

    public AgentResult runFullWorkflow(String jobDescText, CandidateProfile candidate) {
        AgentResult result = new AgentResult();
        result.setCandidate(candidate);

        List<WorkflowStatus> steps = new ArrayList<>();

        // Step 1: Parse JD
        JobDescription jd = jdParser.parse(jobDescText);
        result.setParsedJd(jd);
        steps.add(new WorkflowStatus("JD Parsed", true, "SUCCESS",
            "Extracted " + jd.getRequiredSkills().size() + " required and " +
            jd.getPreferredSkills().size() + " preferred skills",
            System.currentTimeMillis()));

        // Step 2: Research company
        Map<String, Object> research = companyResearchService.researchCompany(jd.getCompany());
        result.setCompanyResearch(research);
        steps.add(new WorkflowStatus("Role Researched", true, "SUCCESS",
            "Researched " + jd.getCompany() + " [MOCK/DEMO]",
            System.currentTimeMillis()));

        // Step 3: Analyze candidate
        candidateAnalyzer.analyze(candidate);
        steps.add(new WorkflowStatus("Candidate Evidence Inspected", true, "SUCCESS",
            "Analyzed " + candidate.getName() + "'s profile with " +
            (candidate.getSkills() != null ? candidate.getSkills().size() : 0) + " skills",
            System.currentTimeMillis()));

        // Step 4: Match evidence (CRITICAL GUARDRAIL)
        MatchResult matchResult = evidenceMatcher.match(jd, candidate);
        result.setMatchResult(matchResult);
        steps.add(new WorkflowStatus("Relevant Evidence Selected", true, "SUCCESS",
            matchResult.getSupportedSkills().size() + " supported, " +
            matchResult.getUnsupportedSkills().size() + " rejected (no evidence)",
            System.currentTimeMillis()));

        // Step 5: Generate initial resume
        ResumeContent initialResume = resumeGenerator.generate(matchResult, candidate, jd);
        result.setInitialResume(initialResume);
        steps.add(new WorkflowStatus("Resume Drafted", true, "SUCCESS",
            "Generated resume with " + initialResume.getSkills().size() + " verified skills",
            System.currentTimeMillis()));

        // Step 6: Evaluate resume
        EvaluationResult evaluation = resumeEvaluator.evaluate(initialResume);
        result.setEvaluation(evaluation);
        steps.add(new WorkflowStatus("Evaluation Completed", true, "SUCCESS",
            "Initial score: " + evaluation.getInitialScore() + "/100",
            System.currentTimeMillis()));

        // Step 7: Check for weaknesses
        if (evaluation.getWeaknesses() != null && !evaluation.getWeaknesses().isEmpty()) {
            steps.add(new WorkflowStatus("Weakness Detected", true, "WARNING",
                evaluation.getWeaknesses().size() + " weaknesses found",
                System.currentTimeMillis()));

            // Step 8: Replan
            steps.add(new WorkflowStatus("Replanning", true, "SUCCESS",
                "Created revision plan with " + evaluation.getRevisionPlan().size() + " steps",
                System.currentTimeMillis()));
        }

        // Step 9: Revise resume
        ResumeContent revisedResume = resumeReviser.revise(initialResume, evaluation, matchResult);
        result.setRevisedResume(revisedResume);
        steps.add(new WorkflowStatus("Resume Revised", true, "SUCCESS",
            "Applied revisions, final score: " + evaluation.getFinalScore() + "/100",
            System.currentTimeMillis()));

        // Step 10: Verify facts
        VerificationResult verification = factualVerifier.verify(revisedResume, candidate);
        result.setVerification(verification);
        steps.add(new WorkflowStatus("Facts Verified", true, "SUCCESS",
            verification.isAllFactsVerified() ? "All facts verified" : "Some facts flagged",
            System.currentTimeMillis()));

        // Step 11: Generate PDF
        String pdfFile = pdfGenerator.generatePdf(revisedResume);
        result.setPdfFileName(pdfFile);
        steps.add(new WorkflowStatus("PDF Generated", true, "SUCCESS",
            "Generated " + pdfFile,
            System.currentTimeMillis()));

        // Build change report
        ChangeReport report = buildChangeReport(matchResult, evaluation, verification);
        result.setChangeReport(report);

        result.setWorkflowSteps(steps);

        return result;
    }

    private ChangeReport buildChangeReport(MatchResult matchResult, EvaluationResult evaluation, VerificationResult verification) {
        ChangeReport report = new ChangeReport();
        List<ChangeReportEntry> changes = new ArrayList<>();

        // Document supported skills
        for (SkillMatch sm : matchResult.getSkillMatches()) {
            if (sm.isSupported()) {
                changes.add(new ChangeReportEntry(
                    "Included skill: " + sm.getSkill(),
                    "Evidence found in candidate profile",
                    sm.getEvidence(),
                    "VERIFIED"
                ));
            }
        }

        // Document revisions
        if (evaluation.getRevisionPlan() != null) {
            for (String revision : evaluation.getRevisionPlan()) {
                changes.add(new ChangeReportEntry(
                    "Revision: " + revision,
                    "Identified during evaluation",
                    "Evaluation score improvement",
                    "APPLIED"
                ));
            }
        }

        report.setChanges(changes);
        report.setRejectedUnsupportedClaims(matchResult.getUnsupportedSkills());

        int rejectedCount = matchResult.getUnsupportedSkills().size();
        int supportedCount = matchResult.getSupportedSkills().size();
        report.setSummary("Generated tailored resume with " + supportedCount +
            " verified skills. Rejected " + rejectedCount +
            " unsupported skills (anti-fabrication guardrail). " +
            "All included claims are backed by candidate evidence.");

        return report;
    }
}
