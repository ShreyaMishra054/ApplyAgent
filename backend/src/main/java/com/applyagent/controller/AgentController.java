package com.applyagent.controller;

import com.applyagent.dto.RunAgentRequest;
import com.applyagent.dto.RunAgentResponse;
import com.applyagent.model.*;
import com.applyagent.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AgentController {

    @Autowired
    private JdParserService jdParser;
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
    private AgentOrchestratorService orchestratorService;

    @Value("${resume.output.dir:./generated-resumes}")
    private String outputDir;

    @PostMapping("/analyze-jd")
    public ResponseEntity<JobDescription> analyzeJd(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(jdParser.parse(request.get("jobDescription")));
    }

    @PostMapping("/analyze-candidate")
    public ResponseEntity<Map<String, Object>> analyzeCandidate(@RequestBody CandidateProfile candidate) {
        return ResponseEntity.ok(candidateAnalyzer.analyze(candidate));
    }

    @PostMapping("/match-evidence")
    public ResponseEntity<MatchResult> matchEvidence(@RequestBody RunAgentRequest request) {
        JobDescription jd = jdParser.parse(request.getJobDescription());
        return ResponseEntity.ok(evidenceMatcher.match(jd, request.getCandidate()));
    }

    @PostMapping("/generate-resume")
    public ResponseEntity<ResumeContent> generateResume(@RequestBody Map<String, Object> request) {
        // Mock simplification for individual endpoint testing
        // Full object deserialization would normally map this correctly.
        return ResponseEntity.ok(new ResumeContent());
    }

    @PostMapping("/evaluate")
    public ResponseEntity<EvaluationResult> evaluate(@RequestBody ResumeContent resume) {
        return ResponseEntity.ok(resumeEvaluator.evaluate(resume));
    }

    @PostMapping("/revise")
    public ResponseEntity<ResumeContent> revise(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(new ResumeContent());
    }

    @PostMapping("/verify")
    public ResponseEntity<VerificationResult> verify(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(new VerificationResult());
    }

    @PostMapping("/run-agent")
    public ResponseEntity<RunAgentResponse> runAgent(@RequestBody RunAgentRequest request) {
        try {
            AgentResult result = orchestratorService.runFullWorkflow(request.getJobDescription(), request.getCandidate());
            return ResponseEntity.ok(new RunAgentResponse(true, result, "Agent completed successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new RunAgentResponse(false, null, e.getMessage()));
        }
    }

    @GetMapping("/download-resume")
    public ResponseEntity<Resource> downloadResume(@RequestParam String fileName) {
        try {
            Path file = Paths.get(outputDir).resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
