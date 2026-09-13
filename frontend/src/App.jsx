import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import UploadPage from './pages/UploadPage';
import WorkflowPage from './pages/WorkflowPage';
import JdAnalysisPage from './pages/JdAnalysisPage';
import EvidenceMatchingPage from './pages/EvidenceMatchingPage';
import ResumePreviewPage from './pages/ResumePreviewPage';
import EvaluationPage from './pages/EvaluationPage';
import ChangeReportPage from './pages/ChangeReportPage';

function App() {
  return (
    <div className="flex h-screen bg-gray-50 overflow-hidden">
      <Sidebar />
      <div className="flex-1 flex flex-col min-w-0 overflow-hidden">
        <Navbar />
        <main className="flex-1 overflow-y-auto p-4 md:p-6">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/upload" element={<UploadPage />} />
            <Route path="/workflow" element={<WorkflowPage />} />
            <Route path="/jd-analysis" element={<JdAnalysisPage />} />
            <Route path="/evidence" element={<EvidenceMatchingPage />} />
            <Route path="/resume" element={<ResumePreviewPage />} />
            <Route path="/evaluation" element={<EvaluationPage />} />
            <Route path="/report" element={<ChangeReportPage />} />
          </Routes>
        </main>
      </div>
    </div>
  );
}

export default App;
