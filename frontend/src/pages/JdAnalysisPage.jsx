import React from 'react';
import { useAgent } from '../context/AgentContext';
import { Link } from 'react-router-dom';

export default function JdAnalysisPage() {
  const { agentResult } = useAgent();

  if (!agentResult || !agentResult.parsedJd) {
    return (
      <div className="text-center mt-20">
        <p>No analysis data available. Run the agent first.</p>
        <Link to="/upload" className="text-primary-600 underline mt-2 block">Go to Upload</Link>
      </div>
    );
  }

  const parsedJd = agentResult.parsedJd;
  const companyResearch = agentResult.companyResearch || {};

  return (
    <div className="max-w-5xl mx-auto space-y-6">
      <h1 className="text-2xl font-bold">JD Analysis Results</h1>

      <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
        <div className="border-b border-gray-100 pb-4 mb-4">
          <h2 className="text-3xl font-bold text-gray-800">{parsedJd.title}</h2>
          <p className="text-xl text-gray-600 mt-1">{parsedJd.company} • {parsedJd.experienceLevel}</p>
        </div>

        <div className="grid md:grid-cols-2 gap-8">
          <div>
            <h3 className="font-bold text-lg mb-3">Required Skills</h3>
            <div className="flex flex-wrap gap-2">
              {(parsedJd.requiredSkills || []).map((skill, idx) => (
                <span key={idx} className="bg-green-100 text-green-800 px-3 py-1 rounded-full text-sm font-medium border border-green-200">
                  {skill}
                </span>
              ))}
            </div>

            <h3 className="font-bold text-lg mt-6 mb-3">Preferred Skills</h3>
            <div className="flex flex-wrap gap-2">
              {(parsedJd.preferredSkills || []).map((skill, idx) => (
                <span key={idx} className="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-sm font-medium border border-blue-200">
                  {skill}
                </span>
              ))}
            </div>
          </div>

          <div>
            <h3 className="font-bold text-lg mb-3">Key Responsibilities</h3>
            <ul className="list-disc pl-5 space-y-2 text-gray-700">
              {(parsedJd.responsibilities || []).map((resp, idx) => (
                <li key={idx}>{resp}</li>
              ))}
            </ul>
          </div>
        </div>
      </div>

      <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
        <div className="flex items-center gap-2 mb-4">
          <h3 className="font-bold text-lg">Company Research & Context</h3>
          <span className="bg-gray-100 text-gray-500 text-xs px-2 py-1 rounded uppercase tracking-wider font-bold">Mock/Demo Research</span>
        </div>
        <div className="bg-gray-50 p-4 rounded-lg text-gray-700 text-sm space-y-2">
          {companyResearch.companyName && <p><strong>Company:</strong> {companyResearch.companyName}</p>}
          {companyResearch.industry && <p><strong>Industry:</strong> {companyResearch.industry}</p>}
          {companyResearch.culture && <p><strong>Culture:</strong> {companyResearch.culture}</p>}
          {companyResearch.techStack && <p><strong>Tech Stack:</strong> {companyResearch.techStack}</p>}
          {companyResearch.recentNews && <p><strong>Recent News:</strong> {companyResearch.recentNews}</p>}
        </div>
      </div>
    </div>
  );
}
