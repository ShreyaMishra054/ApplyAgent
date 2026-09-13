import React from 'react';
import { useAgent } from '../context/AgentContext';
import { Link } from 'react-router-dom';

export default function ChangeReportPage() {
  const { agentResult } = useAgent();

  if (!agentResult || !agentResult.changeReport) {
    return (
      <div className="text-center mt-20">
        <p>No report data available. Run the agent first.</p>
        <Link to="/upload" className="text-primary-600 underline mt-2 block">Go to Upload</Link>
      </div>
    );
  }

  const report = agentResult.changeReport;
  const verification = agentResult.verification;
  const verificationPassed = verification && verification.allFactsVerified;

  return (
    <div className="max-w-5xl mx-auto space-y-6">
      <h1 className="text-2xl font-bold mb-6">Final Verification & Change Report</h1>

      <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
        <h3 className="font-bold text-lg mb-2">Executive Summary</h3>
        <p className="text-gray-700">{report.summary}</p>
      </div>

      <div className="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
        <div className="px-6 py-4 border-b border-gray-200 bg-gray-50 flex justify-between items-center">
          <h3 className="font-bold text-lg">Applied Changes Log</h3>
          <span className={`px-3 py-1 rounded-full text-xs font-bold ${verificationPassed ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>
            VERIFICATION {verificationPassed ? 'PASSED' : 'PENDING'}
          </span>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full text-left border-collapse">
            <thead>
              <tr className="border-b border-gray-200 text-sm text-gray-500">
                <th className="p-4 font-medium">Change Applied</th>
                <th className="p-4 font-medium">Reasoning</th>
                <th className="p-4 font-medium">Supporting Evidence</th>
                <th className="p-4 font-medium text-center">Verified</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-100">
              {(report.changes || []).map((change, idx) => (
                <tr key={idx} className="text-sm">
                  <td className="p-4 font-medium text-gray-800">{change.change}</td>
                  <td className="p-4 text-gray-600">{change.reason}</td>
                  <td className="p-4 text-gray-600">{change.supportingEvidence}</td>
                  <td className="p-4 text-center">
                    {change.verificationResult === 'VERIFIED' || change.verificationResult === 'APPLIED' ? (
                      <span className="text-green-500 font-bold">✓</span>
                    ) : (
                      <span className="text-red-500 font-bold">✕</span>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {verification && verification.items && (
        <div className="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
          <div className="px-6 py-4 border-b border-gray-200 bg-green-50">
            <h3 className="font-bold text-lg text-green-800">Factual Verification Details</h3>
          </div>
          <ul className="divide-y divide-gray-100">
            {verification.items.map((item, idx) => (
              <li key={idx} className="p-4 flex items-center justify-between">
                <div className="flex items-center gap-2">
                  <span className={item.verified ? 'text-green-500' : 'text-red-500'}>
                    {item.verified ? '✓' : '✕'}
                  </span>
                  <span className="font-medium text-sm">{item.claim}</span>
                </div>
                <div className="text-xs text-gray-500 bg-gray-50 px-2 py-1 rounded">
                  {item.status}: {item.evidence}
                </div>
              </li>
            ))}
          </ul>
        </div>
      )}

      <div className="bg-red-50 rounded-xl shadow-sm border border-red-200 p-6">
        <h3 className="font-bold text-red-800 mb-2">Rejected Unsupported Claims</h3>
        <p className="text-sm text-red-600 mb-4">These claims were rejected because no supporting candidate evidence was found.</p>
        <div className="flex flex-wrap gap-2">
          {(report.rejectedUnsupportedClaims || []).map((claim, idx) => (
            <span key={idx} className="bg-white border border-red-200 text-red-700 px-3 py-1 rounded text-sm font-medium flex items-center gap-1">
              <span className="text-xs">✕</span> {claim}
            </span>
          ))}
        </div>
      </div>
    </div>
  );
}
