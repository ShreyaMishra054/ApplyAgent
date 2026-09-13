import React from 'react';
import { Link } from 'react-router-dom';
import { useAgent } from '../context/AgentContext';

export default function WorkflowPage() {
  const { agentResult, loading } = useAgent();

  if (loading) {
    return (
      <div className="flex flex-col items-center justify-center h-full">
        <div className="animate-spin rounded-full h-16 w-16 border-b-4 border-primary-600 mb-4"></div>
        <p className="text-xl font-medium text-gray-700">Agent is working on your application...</p>
        <p className="text-gray-500 mt-2">This may take a moment.</p>
      </div>
    );
  }

  if (!agentResult) {
    return (
      <div className="text-center mt-20">
        <h2 className="text-2xl font-bold mb-4">No workflow data</h2>
        <p className="text-gray-600 mb-6">Run the agent first to see the workflow progress.</p>
        <Link to="/upload" className="bg-primary-600 text-white py-2 px-6 rounded-lg font-medium">
          Go to Upload
        </Link>
      </div>
    );
  }

  const steps = agentResult.workflowSteps || [];

  const getStatusIcon = (status) => {
    switch (status) {
      case 'SUCCESS': return <span className="text-green-500 text-xl">✓</span>;
      case 'WARNING': return <span className="text-amber-500 text-xl">⚠</span>;
      case 'IN_PROGRESS': return <span className="text-blue-500 text-xl">→</span>;
      default: return <span className="text-gray-300 text-xl">○</span>;
    }
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'SUCCESS': return 'border-green-500';
      case 'WARNING': return 'border-amber-500';
      case 'IN_PROGRESS': return 'border-blue-500';
      default: return 'border-gray-200';
    }
  };

  return (
    <div className="max-w-4xl mx-auto">
      <h1 className="text-2xl font-bold mb-6">Agent Workflow Status</h1>
      
      <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-8">
        <div className="relative border-l-2 border-gray-200 ml-3 md:ml-4 space-y-8">
          {steps.map((s, idx) => (
            <div key={idx} className="relative pl-8">
              <div className={`absolute -left-[17px] top-1 bg-white border-2 rounded-full w-8 h-8 flex items-center justify-center ${getStatusColor(s.status)}`}>
                {getStatusIcon(s.status)}
              </div>
              <div>
                <h3 className="font-bold text-gray-800">{s.step}</h3>
                {s.message && <p className="text-sm text-gray-600 mt-1">{s.message}</p>}
              </div>
            </div>
          ))}
        </div>
      </div>

      <div className="mt-8 flex flex-wrap gap-4 justify-center">
        <Link to="/jd-analysis" className="bg-white border border-gray-300 hover:bg-gray-50 text-gray-700 py-2 px-4 rounded shadow-sm">📋 JD Analysis</Link>
        <Link to="/evidence" className="bg-white border border-gray-300 hover:bg-gray-50 text-gray-700 py-2 px-4 rounded shadow-sm">🔍 Evidence</Link>
        <Link to="/evaluation" className="bg-white border border-gray-300 hover:bg-gray-50 text-gray-700 py-2 px-4 rounded shadow-sm">📊 Evaluation</Link>
        <Link to="/resume" className="bg-primary-600 hover:bg-primary-700 text-white font-medium py-2 px-4 rounded shadow-sm">📄 View Final Resume</Link>
        <Link to="/report" className="bg-white border border-gray-300 hover:bg-gray-50 text-gray-700 py-2 px-4 rounded shadow-sm">📝 Change Report</Link>
      </div>
    </div>
  );
}
