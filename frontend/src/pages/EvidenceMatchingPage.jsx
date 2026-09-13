import React from 'react';
import { useAgent } from '../context/AgentContext';
import { Link } from 'react-router-dom';

export default function EvidenceMatchingPage() {
  const { agentResult } = useAgent();

  if (!agentResult || !agentResult.matchResult) {
    return (
      <div className="text-center mt-20">
        <p>No matching data available. Run the agent first.</p>
        <Link to="/upload" className="text-primary-600 underline mt-2 block">Go to Upload</Link>
      </div>
    );
  }

  const matchResult = agentResult.matchResult;
  const skillMatches = matchResult.skillMatches || [];
  const supported = skillMatches.filter(sm => sm.supported);
  const unsupported = skillMatches.filter(sm => !sm.supported);
  const total = skillMatches.length;
  const matchPercentage = total > 0 ? Math.round((supported.length / total) * 100) : 0;

  return (
    <div className="max-w-5xl mx-auto space-y-6">
      <h1 className="text-2xl font-bold">Evidence Matching & Guardrails</h1>

      <div className="bg-red-50 border-l-4 border-red-500 p-4 rounded-r-lg shadow-sm">
        <div className="flex">
          <div className="flex-shrink-0">
            <span className="text-red-500 text-xl font-bold">🛡️</span>
          </div>
          <div className="ml-3">
            <h3 className="text-lg font-bold text-red-800">Anti-Fabrication Guardrail Active</h3>
            <p className="text-red-700 mt-1">
              {unsupported.length} skill(s) requested in the JD were rejected due to lack of supporting evidence in the candidate profile. These skills will NOT appear on the generated resume.
            </p>
          </div>
        </div>
      </div>

      <div className="grid md:grid-cols-3 gap-6">
        <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6 flex flex-col items-center justify-center">
          <h3 className="text-gray-500 font-medium uppercase tracking-wider text-sm mb-4">Match Score</h3>
          <div className="relative w-32 h-32 flex items-center justify-center bg-gray-50 rounded-full border-4 border-primary-100">
            <span className="text-4xl font-bold text-primary-600">{matchPercentage}%</span>
          </div>
          <p className="mt-4 font-medium text-gray-700">{supported.length} of {total} skills supported</p>
        </div>

        <div className="md:col-span-2 space-y-6">
          <div className="bg-white rounded-xl shadow-sm border border-green-200 overflow-hidden">
            <div className="bg-green-50 px-4 py-3 border-b border-green-100">
              <h3 className="font-bold text-green-800">✓ Supported Skills (Included in Resume)</h3>
            </div>
            <ul className="divide-y divide-gray-100">
              {supported.map((sm, idx) => (
                <li key={idx} className="p-4 flex flex-col sm:flex-row sm:items-center justify-between gap-2">
                  <div className="flex items-center gap-2">
                    <span className="text-green-500 font-bold">✓</span>
                    <span className="font-medium">{sm.skill}</span>
                  </div>
                  <div className="text-sm text-gray-500 bg-gray-50 px-3 py-1 rounded">
                    {sm.evidence}
                  </div>
                </li>
              ))}
            </ul>
          </div>

          <div className="bg-white rounded-xl shadow-sm border border-red-200 overflow-hidden">
            <div className="bg-red-50 px-4 py-3 border-b border-red-100">
              <h3 className="font-bold text-red-800">✕ Unsupported Skills (EXCLUDED from Resume)</h3>
            </div>
            <ul className="divide-y divide-gray-100">
              {unsupported.map((sm, idx) => (
                <li key={idx} className="p-4 flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <span className="text-red-500 font-bold">✕</span>
                    <span className="font-medium text-gray-700">{sm.skill}</span>
                  </div>
                  <span className="text-xs font-bold text-red-600 bg-red-100 px-2 py-1 rounded uppercase">
                    No Evidence Found — Rejected
                  </span>
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}
