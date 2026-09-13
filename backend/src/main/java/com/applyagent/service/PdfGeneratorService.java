package com.applyagent.service;

import com.applyagent.model.ResumeContent;
import com.applyagent.model.ResumeSkill;
import com.applyagent.model.ResumeProject;
import com.applyagent.model.ResumeExperience;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileOutputStream;
import java.io.File;
import java.util.UUID;

@Service
public class PdfGeneratorService {

    @Value("${resume.output.dir:./generated-resumes}")
    private String outputDir;

    public String generatePdf(ResumeContent resume) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            String fileName = "Resume_" + UUID.randomUUID().toString() + ".pdf";
            String filePath = outputDir + "/" + fileName;
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            
            document.open();
            
            document.add(new Paragraph("Name: " + resume.getCandidateName()));
            document.add(new Paragraph("Target Role: " + resume.getTargetRole()));
            document.add(new Paragraph("Education: " + resume.getEducation()));
            document.add(new Paragraph("\nSummary: " + resume.getSummary()));
            
            document.add(new Paragraph("\nSkills:"));
            if (resume.getSkills() != null) {
                for (ResumeSkill s : resume.getSkills()) {
                    document.add(new Paragraph("- " + s.getSkill()));
                }
            }
            
            document.add(new Paragraph("\nProjects:"));
            if (resume.getProjects() != null) {
                for (ResumeProject p : resume.getProjects()) {
                    document.add(new Paragraph("- " + p.getName() + ": " + p.getDescription()));
                }
            }
            
            document.add(new Paragraph("\nExperience:"));
            if (resume.getExperiences() != null) {
                for (ResumeExperience e : resume.getExperiences()) {
                    document.add(new Paragraph("- " + e.getRole() + " at " + e.getCompany() + " (" + e.getDuration() + ")"));
                }
            }
            
            document.close();
            
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
