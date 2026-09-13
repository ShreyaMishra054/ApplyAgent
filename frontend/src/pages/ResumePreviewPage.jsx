import React from 'react';
import { useAgent } from '../context/AgentContext';
import { getDownloadUrl } from '../api';
import { Link } from 'react-router-dom';

export default function ResumePreviewPage() {
  const { agentResult } = useAgent();

  if (!agentResult) {
    return (
      <div className="text-center mt-20">
        <p>No resume generated yet. Run the agent first.</p>
        <Link to="/upload" className="text-primary-600 underline mt-2 block">Go to Upload</Link>
      </div>
    );
  }

  const resume = agentResult.revisedResume || agentResult.initialResume;
  if (!resume) {
    return (
      <div className="text-center mt-20">
        <p>Resume data not available.</p>
        <Link to="/upload" className="text-primary-600 underline mt-2 block">Go to Upload</Link>
      </div>
    );
  }

  const verified = agentResult.verification && agentResult.verification.allFactsVerified;
  const skillNames = (resume.skills || []).map(s => typeof s === 'string' ? s : s.skill);
  const pdfFileName = agentResult.pdfFileName;

  const handleDownload = () => {
    if (pdfFileName) {
      window.open(getDownloadUrl(pdfFileName), '_blank');
    } else {
      alert('PDF file not available');
    }
  };

  return (
    <div className="max-w-4xl mx-auto space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold">Final Verified Resume</h1>
        <div className="flex gap-4">
          {verified && (
            <div className="bg-green-100 text-green-800 px-3 py-2 rounded font-bold text-sm flex items-center gap-1 border border-green-200">
              <span>✓ All Facts Verified</span>
            </div>
          )}
          <button 
            onClick={handleDownload}
            className="bg-primary-600 hover:bg-primary-700 text-white font-medium py-2 px-4 rounded shadow transition flex items-center gap-2"
          >
            <span>📄</span> Download PDF
          </button>
        </div>
      </div>

      <div className="bg-white p-10 rounded-xl shadow-lg border border-gray-200 min-h-[800px] font-sans text-gray-800">
        <div className="text-center border-b-2 border-gray-800 pb-6 mb-6">
          <h1 className="text-4xl font-bold tracking-tight">{resume.candidateName}</h1>
          <p className="mt-2 text-gray-600 text-sm">Target Role: {resume.targetRole}</p>
        </div>

        <div className="mb-6">
          <h2 className="text-lg font-bold uppercase border-b border-gray-300 pb-1 mb-3">Professional Summary</h2>
          <p className="text-sm leading-relaxed">{resume.summary}</p>
        </div>

        <div className="mb-6">
          <h2 className="text-lg font-bold uppercase border-b border-gray-300 pb-1 mb-3">Technical Skills</h2>
          <div className="flex flex-wrap gap-2">
            {skillNames.map((skill, idx) => (
              <span key={idx} className="bg-primary-50 text-primary-700 px-3 py-1 rounded-full text-sm font-medium border border-primary-200">
                {skill}
              </span>
            ))}
          </div>
        </div>

        <div className="mb-6">
          <h2 className="text-lg font-bold uppercase border-b border-gray-300 pb-1 mb-3">Projects</h2>
          <div className="space-y-4">
            {(resume.projects || []).map((proj, idx) => (
              <div key={idx}>
                <h3 className="font-bold text-md">{proj.name}</h3>
                <p className="text-xs italic text-gray-600 mb-1">
                  Technologies: {Array.isArray(proj.technologies) ? proj.technologies.join(', ') : proj.technologies}
                </p>
                {proj.description && (
                  <ul className="list-disc pl-5 text-sm space-y-1">
                    <li>{proj.description}</li>
                  </ul>
                )}
                {proj.relevance && <p className="text-xs text-primary-600 mt-1">{proj.relevance}</p>}
              </div>
            ))}
          </div>
        </div>

        <div className="mb-6">
          <h2 className="text-lg font-bold uppercase border-b border-gray-300 pb-1 mb-3">Education</h2>
          <p className="text-sm font-bold">{resume.education}</p>
        </div>

        {resume.experiences && resume.experiences.length > 0 && (
          <div className="mb-6">
            <h2 className="text-lg font-bold uppercase border-b border-gray-300 pb-1 mb-3">Experience</h2>
            {resume.experiences.map((exp, idx) => (
              <div key={idx} className="mb-2">
                <p className="font-bold text-sm">{exp.role} at {exp.company} ({exp.duration})</p>
                <p className="text-sm text-gray-600">{exp.description}</p>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
