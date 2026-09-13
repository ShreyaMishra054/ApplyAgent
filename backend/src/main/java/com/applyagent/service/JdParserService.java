package com.applyagent.service;

import com.applyagent.model.JobDescription;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JdParserService {

    private static final List<String> KNOWN_SKILLS = Arrays.asList(
        "Java", "Spring Boot", "REST APIs", "SQL", "MySQL", "Git",
        "AWS", "Docker", "React", "DSA", "Python", "JavaScript",
        "TypeScript", "Node.js", "Kubernetes", "MongoDB", "PostgreSQL",
        "Redis", "GraphQL", "CI/CD", "Jenkins", "Terraform",
        "Microservices", "Agile", "Scrum", "HTML", "CSS", "Angular",
        "Vue.js", "C++", "C#", ".NET", "Go", "Rust", "Kafka",
        "RabbitMQ", "Elasticsearch", "Linux", "Data Structures"
    );

    public JobDescription parse(String rawText) {
        JobDescription jd = new JobDescription();
        jd.setRawText(rawText);

        // Extract title from first non-empty line
        String[] lines = rawText.split("\\n");
        String title = "Software Engineer";
        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                title = trimmed;
                break;
            }
        }
        jd.setTitle(title);

        // Try to extract company name
        String company = "Tech Company";
        String lowerText = rawText.toLowerCase();
        if (lowerText.contains("company:")) {
            int idx = lowerText.indexOf("company:");
            String after = rawText.substring(idx + 8).trim();
            int end = after.indexOf("\n");
            company = end > 0 ? after.substring(0, end).trim() : after.trim();
        }
        jd.setCompany(company);

        // Determine experience level
        if (lowerText.contains("intern")) {
            jd.setExperienceLevel("Intern");
        } else if (lowerText.contains("senior") || lowerText.contains("sr.")) {
            jd.setExperienceLevel("Senior");
        } else if (lowerText.contains("junior") || lowerText.contains("jr.") || lowerText.contains("entry")) {
            jd.setExperienceLevel("Junior");
        } else {
            jd.setExperienceLevel("Mid Level");
        }

        // Parse required and preferred skills from sections
        List<String> requiredSkills = new ArrayList<>();
        List<String> preferredSkills = new ArrayList<>();
        List<String> responsibilities = new ArrayList<>();

        // Try to find "Required" and "Preferred" sections
        String[] sections = rawText.split("(?i)(required\\s*skills?:|preferred\\s*skills?:|nice\\s*to\\s*have:|responsibilities:|qualifications:)");

        if (sections.length > 1) {
            // We found sections - parse them properly
            Pattern sectionPattern = Pattern.compile(
                "(?i)(required\\s*skills?:|preferred\\s*skills?:|nice\\s*to\\s*have:|responsibilities:|qualifications:)",
                Pattern.MULTILINE
            );
            Matcher sectionMatcher = sectionPattern.matcher(rawText);

            List<String> sectionNames = new ArrayList<>();
            List<Integer> sectionStarts = new ArrayList<>();

            while (sectionMatcher.find()) {
                sectionNames.add(sectionMatcher.group().toLowerCase().trim());
                sectionStarts.add(sectionMatcher.end());
            }

            for (int i = 0; i < sectionNames.size(); i++) {
                int start = sectionStarts.get(i);
                int end = (i + 1 < sectionStarts.size())
                    ? rawText.lastIndexOf('\n', sectionStarts.get(i + 1))
                    : rawText.length();
                if (end < start) end = rawText.length();

                String sectionContent = rawText.substring(start, end);
                List<String> items = extractListItems(sectionContent);

                String sectionName = sectionNames.get(i);
                if (sectionName.contains("required") || sectionName.contains("qualification")) {
                    requiredSkills.addAll(matchSkills(items));
                } else if (sectionName.contains("preferred") || sectionName.contains("nice")) {
                    preferredSkills.addAll(matchSkills(items));
                } else if (sectionName.contains("responsibilit")) {
                    responsibilities.addAll(items);
                }
            }
        }

        // Fallback: if no skills found via sections, scan entire text
        if (requiredSkills.isEmpty() && preferredSkills.isEmpty()) {
            for (String skill : KNOWN_SKILLS) {
                if (rawText.toLowerCase().contains(skill.toLowerCase())) {
                    requiredSkills.add(skill);
                }
            }
        }

        jd.setRequiredSkills(requiredSkills);
        jd.setPreferredSkills(preferredSkills);
        jd.setResponsibilities(responsibilities);

        return jd;
    }

    private List<String> extractListItems(String text) {
        List<String> items = new ArrayList<>();
        String[] lines = text.split("\\n");
        for (String line : lines) {
            String trimmed = line.trim();
            // Remove bullet markers
            trimmed = trimmed.replaceAll("^[-•*>]+\\s*", "");
            trimmed = trimmed.replaceAll("^\\d+\\.\\s*", "");
            trimmed = trimmed.trim();
            // Skip empty, too short, or section headers
            if (trimmed.isEmpty() || trimmed.length() <= 1) continue;
            if (trimmed.endsWith(":")) continue;  // section header like "Required Skills:"
            if (trimmed.toLowerCase().contains("skills:")) continue;
            if (trimmed.toLowerCase().contains("responsibilities:")) continue;
            if (trimmed.toLowerCase().contains("qualifications:")) continue;
            if (trimmed.toLowerCase().contains("requirements:")) continue;
            items.add(trimmed);
        }
        return items;
    }

    private List<String> matchSkills(List<String> items) {
        List<String> matched = new ArrayList<>();
        for (String item : items) {
            String lowerItem = item.toLowerCase().trim();
            // First check if the item IS a known skill
            boolean directMatch = false;
            for (String skill : KNOWN_SKILLS) {
                if (lowerItem.equalsIgnoreCase(skill) ||
                    lowerItem.equals(skill.toLowerCase())) {
                    if (!matched.contains(skill)) {
                        matched.add(skill);
                    }
                    directMatch = true;
                    break;
                }
            }
            // If not a direct match, check if it contains a known skill
            if (!directMatch) {
                for (String skill : KNOWN_SKILLS) {
                    if (lowerItem.contains(skill.toLowerCase()) && !matched.contains(skill)) {
                        matched.add(skill);
                    }
                }
                // If still no match, add the raw item as a skill
                if (!directMatch) {
                    boolean foundAny = false;
                    for (String m : matched) {
                        if (item.toLowerCase().contains(m.toLowerCase())) {
                            foundAny = true;
                            break;
                        }
                    }
                    if (!foundAny && item.length() < 30) {
                        matched.add(item);
                    }
                }
            }
        }
        return matched;
    }
}
