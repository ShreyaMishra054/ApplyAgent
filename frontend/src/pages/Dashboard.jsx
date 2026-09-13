import React from 'react';
import { Link } from 'react-router-dom';

export default function Dashboard() {
  return (
    <div className="max-w-5xl mx-auto">
      <div className="bg-white rounded-xl shadow-sm overflow-hidden mb-8">
        <div className="bg-primary-600 px-8 py-12 text-white text-center">
          <h1 className="text-4xl font-bold mb-4">ApplyAgent</h1>
          <p className="text-xl text-primary-100">Autonomous Resume & Application Agent</p>
        </div>
        <div className="p-8">
          <p className="text-gray-600 text-lg mb-8 text-center max-w-3xl mx-auto">
            ApplyAgent intelligently analyzes job descriptions, cross-references with your candidate profile,
            and autonomously generates a customized, evidence-based resume tailored specifically for the role.
          </p>
          
          <div className="flex justify-center gap-4">
            <Link to="/upload" className="bg-primary-600 hover:bg-primary-700 text-white font-medium py-3 px-6 rounded-lg shadow transition">
              Start New Application
            </Link>
            <Link to="/workflow" className="bg-white border border-gray-300 hover:bg-gray-50 text-gray-700 font-medium py-3 px-6 rounded-lg shadow-sm transition">
              View Workflow
            </Link>
          </div>
        </div>
      </div>

      <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
          <div className="text-3xl mb-4">🛡️</div>
          <h3 className="text-lg font-bold mb-2">Anti-Fabrication Guardrail</h3>
          <p className="text-gray-600 text-sm">Ensures every skill claimed on the resume is backed by real evidence from your profile.</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
          <div className="text-3xl mb-4">⚡</div>
          <h3 className="text-lg font-bold mb-2">Autonomous Workflow</h3>
          <p className="text-gray-600 text-sm">11-step intelligent process that analyzes, drafts, evaluates, and revises automatically.</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
          <div className="text-3xl mb-4">🔍</div>
          <h3 className="text-lg font-bold mb-2">Evidence-Based</h3>
          <p className="text-gray-600 text-sm">Maps job requirements directly to your past projects and experience.</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
          <div className="text-3xl mb-4">📄</div>
          <h3 className="text-lg font-bold mb-2">PDF Generation</h3>
          <p className="text-gray-600 text-sm">Outputs a cleanly formatted, ATS-friendly PDF ready for submission.</p>
        </div>
      </div>
    </div>
  );
}
