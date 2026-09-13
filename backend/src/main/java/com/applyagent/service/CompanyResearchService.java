package com.applyagent.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;

@Service
public class CompanyResearchService {

    public Map<String, Object> researchCompany(String companyName) {
        Map<String, Object> research = new HashMap<>();
        research.put("companyName", companyName);
        research.put("industry", "[MOCK/DEMO RESEARCH] Software Technology");
        research.put("culture", "[MOCK/DEMO RESEARCH] Fast-paced, innovative, values open source contribution.");
        research.put("recentNews", "[MOCK/DEMO RESEARCH] Recently launched a new AI-driven product suite.");
        research.put("techStack", "[MOCK/DEMO RESEARCH] Java, Spring Boot, React, AWS, Docker, Kubernetes.");
        return research;
    }
}
