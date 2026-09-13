import React from 'react';
import { useAgent } from '../context/AgentContext';
import { Link } from 'react-router-dom';
import ScoreCard from '../components/ScoreCard';

export default function EvaluationPage() {
  const { agentResult } = useAgent();

  if (!agentResult || !agentResult.evaluation) {
    return (
      <div className="text-center mt-20">
        <p>No evaluation data available. Run the agent first.</p>
        <Link to="/upload" className="text-primary-600 underline mt-2 block">Go to Upload</Link>
      </div>
    );
  }

  const evalData = agentResult.evaluation;

  return (
    <div className="max-w-5xl mx-auto space-y-8">
      <div>
        <h1 className="text-2xl font-bold mb-2">Resume Evaluation</h1>
        <p className="text-sm text-gray-500 bg-gray-100 p-2 rounded inline-block">
          Note: These are prototype/demo scores for hackathon evaluation purposes. They are NOT from a commercial ATS system.
        </p>
      </div>

      <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
        <ScoreCard label="ATS/Relevance" score={evalData.atsScore} max={100} />
        <ScoreCard label="Role Match" score={evalData.roleMatchScore} max={100} />
        <ScoreCard label="Formatting" score={evalData.formattingScore} max={100} />
        <ScoreCard label="Factual Consistency" score={evalData.factualConsistencyScore} max={100} />
        
        <div className="bg-gray-50 rounded-xl p-4 border border-gray-200">
          <p className="text-sm text-gray-500 mb-1">Initial Score</p>
          <p className="text-3xl font-bold text-gray-700">{evalData.initialScore}/100</p>
        </div>
        
        <div className="bg-primary-50 rounded-xl p-4 border border-primary-200">
          <p className="text-sm text-primary-600 mb-1 font-bold">Final Score (After Revision)</p>
          <p className="text-3xl font-bold text-primary-700">{evalData.finalScore}/100</p>
        </div>
      </div>

      <div className="grid md:grid-cols-2 gap-6">
        <div className="bg-white rounded-xl shadow-sm border border-amber-200 overflow-hidden">
          <div className="bg-amber-50 px-4 py-3 border-b border-amber-100">
            <h3 className="font-bold text-amber-800">Detected Weaknesses (Initial Draft)</h3>
          </div>
          <ul className="p-4 space-y-3">
            {(evalData.weaknesses || []).map((weakness, idx) => (
              <li key={idx} className="flex items-start gap-3 text-gray-700">
                <span className="text-amber-500 mt-0.5">⚠</span>
                <span className="text-sm">{weakness}</span>
              </li>
            ))}
          </ul>
        </div>

        <div className="bg-white rounded-xl shadow-sm border border-blue-200 overflow-hidden">
          <div className="bg-blue-50 px-4 py-3 border-b border-blue-100">
            <h3 className="font-bold text-blue-800">Autonomous Revision Plan</h3>
          </div>
          <ul className="p-4 space-y-3">
            {(evalData.revisionPlan || []).map((plan, idx) => (
              <li key={idx} className="flex items-start gap-3 text-gray-700">
                <span className="text-blue-500 mt-0.5">→</span>
                <span className="text-sm">{plan}</span>
              </li>
            ))}
          </ul>
        </div>
      </div>

      <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
        <h3 className="font-bold text-lg mb-3">Overall Agent Assessment</h3>
        <p className="text-gray-700 text-sm leading-relaxed">{evalData.overallAssessment}</p>
      </div>
    </div>
  );
}
