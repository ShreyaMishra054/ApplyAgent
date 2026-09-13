package com.applyagent.service;

import com.applyagent.model.CandidateProfile;
import com.applyagent.model.ResumeContent;
import com.applyagent.model.VerificationResult;
import com.applyagent.model.VerificationItem;
import com.applyagent.model.ResumeSkill;
import com.applyagent.model.ResumeProject;
import com.applyagent.model.Project;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class FactualVerifierService {

    public VerificationResult verify(ResumeContent resume, CandidateProfile candidate) {
        List<VerificationItem> items = new ArrayList<>();
        List<String> rejected = new ArrayList<>();
        boolean allVerified = true;

        // Verify each skill in the resume
        if (resume.getSkills() != null) {
            for (ResumeSkill rs : resume.getSkills()) {
                boolean found = false;
                String evidence = "";

                // Check candidate skills list
                if (candidate.getSkills() != null) {
                    for (String cs : candidate.getSkills()) {
                        if (cs.toLowerCase().contains(rs.getSkill().toLowerCase()) ||
                            rs.getSkill().toLowerCase().contains(cs.toLowerCase())) {
                            found = true;
                            evidence = "Found in candidate skills list: " + cs;
                            break;
                        }
                    }
                }

                // Check project technologies
                if (!found && candidate.getProjects() != null) {
                    for (Project p : candidate.getProjects()) {
                        if (p.getTechnologies() != null) {
                            for (String tech : p.getTechnologies()) {
                                if (tech.toLowerCase().contains(rs.getSkill().toLowerCase()) ||
                                    rs.getSkill().toLowerCase().contains(tech.toLowerCase())) {
                                    found = true;
                                    evidence = "Found in project '" + p.getName() + "' technologies";
                                    break;
                                }
                            }
                        }
                        if (found) break;
                    }
                }

                if (found) {
                    items.add(new VerificationItem("Skill: " + rs.getSkill(), true, evidence, "VERIFIED"));
                } else {
                    items.add(new VerificationItem("Skill: " + rs.getSkill(), false,
                        "No supporting evidence found", "REJECTED"));
                    rejected.add("Skill: " + rs.getSkill());
                    allVerified = false;
                }
            }
        }

        // Verify projects
        if (resume.getProjects() != null && candidate.getProjects() != null) {
            for (ResumeProject rp : resume.getProjects()) {
                boolean found = false;
                for (Project cp : candidate.getProjects()) {
                    if (cp.getName() != null && cp.getName().equalsIgnoreCase(rp.getName())) {
                        found = true;
                        items.add(new VerificationItem("Project: " + rp.getName(), true,
                            "Matches candidate project: " + cp.getName(), "VERIFIED"));
                        break;
                    }
                }
                if (!found) {
                    items.add(new VerificationItem("Project: " + rp.getName(), false,
                        "No matching candidate project", "REJECTED"));
                    rejected.add("Project: " + rp.getName());
                    allVerified = false;
                }
            }
        }

        // Verify education
        if (resume.getEducation() != null && candidate.getEducation() != null) {
            if (resume.getEducation().toLowerCase().contains(candidate.getEducation().toLowerCase()) ||
                candidate.getEducation().toLowerCase().contains(resume.getEducation().toLowerCase())) {
                items.add(new VerificationItem("Education: " + resume.getEducation(), true,
                    "Matches candidate education: " + candidate.getEducation(), "VERIFIED"));
            } else {
                items.add(new VerificationItem("Education: " + resume.getEducation(), false,
                    "Does not match candidate education", "REJECTED"));
                rejected.add("Education: " + resume.getEducation());
                allVerified = false;
            }
        }

        // Verify candidate name
        if (resume.getCandidateName() != null && candidate.getName() != null) {
            if (resume.getCandidateName().equalsIgnoreCase(candidate.getName())) {
                items.add(new VerificationItem("Name: " + resume.getCandidateName(), true,
                    "Matches candidate name", "VERIFIED"));
            } else {
                items.add(new VerificationItem("Name: " + resume.getCandidateName(), false,
                    "Does not match candidate name", "REJECTED"));
                rejected.add("Name mismatch");
                allVerified = false;
            }
        }

        return new VerificationResult(allVerified, items, rejected);
    }
}
