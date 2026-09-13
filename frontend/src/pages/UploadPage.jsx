import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAgent } from '../context/AgentContext';
import { saveCandidate, getSavedCandidates, deleteCandidate } from '../api';
import LoadingSpinner from '../components/LoadingSpinner';

const DEMO_JD = `Software Engineer Intern

We are looking for a motivated Software Engineer Intern to join our team.

Required Skills:
- Java
- Spring Boot  
- REST APIs
- SQL
- Git

Preferred Skills:
- AWS
- Docker
- React
- DSA

Responsibilities:
- Develop and maintain backend services
- Write clean, testable code
- Collaborate with the team on design and implementation
- Participate in code reviews`;

export default function UploadPage() {
  const [jd, setJd] = useState('');
  const [candidate, setCandidate] = useState({
    name: '',
    email: '',
    phone: '',
    education: '',
    skills: '',
    experience: '',
    certifications: '',
    projects: []
  });
  const [savedCandidates, setSavedCandidates] = useState([]);
  const [saving, setSaving] = useState(false);
  const [saveMsg, setSaveMsg] = useState('');

  const { runAgent, loading } = useAgent();
  const navigate = useNavigate();

  // Load saved candidates on mount
  useEffect(() => {
    loadSavedCandidates();
  }, []);

  const loadSavedCandidates = async () => {
    try {
      const res = await getSavedCandidates();
      setSavedCandidates(res.data || []);
    } catch (err) {
      console.log('Could not load saved candidates:', err.message);
    }
  };

  const handleLoadDemo = () => {
    setJd(DEMO_JD);
    setCandidate({
      name: 'Alex Sharma',
      email: 'alex.sharma@email.com',
      phone: '9876543210',
      education: 'B.Tech Computer Science',
      skills: 'Java, Spring Boot, MySQL, React, JavaScript, Git',
      experience: 'None',
      certifications: 'None',
      projects: [
        { name: 'Student Management System', description: 'RESTful API for managing student records', technologies: 'Java, Spring Boot, REST APIs, SQL' },
        { name: 'Portfolio Website', description: 'Responsive personal portfolio', technologies: 'React, JavaScript, Tailwind CSS' }
      ]
    });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCandidate(prev => ({ ...prev, [name]: value }));
  };

  // Save candidate to MySQL
  const handleSaveCandidate = async () => {
    if (!candidate.name) { alert('Please enter a candidate name.'); return; }
    setSaving(true);
    setSaveMsg('');
    try {
      const payload = {
        name: candidate.name,
        email: candidate.email,
        phone: candidate.phone,
        education: candidate.education,
        skills: candidate.skills,
        experience: candidate.experience,
        certifications: candidate.certifications,
        jobDescription: jd,
        projects: candidate.projects.map(p => ({
          name: p.name,
          description: p.description || '',
          technologies: typeof p.technologies === 'string' ? p.technologies : (p.technologies || []).join(', ')
        }))
      };
      await saveCandidate(payload);
      setSaveMsg('✓ Saved to database!');
      loadSavedCandidates();
    } catch (err) {
      setSaveMsg('✕ Save failed: ' + (err.response?.data?.message || err.message));
    } finally {
      setSaving(false);
      setTimeout(() => setSaveMsg(''), 4000);
    }
  };

  // Load a saved candidate from the list
  const handleLoadSaved = (saved) => {
    setCandidate({
      name: saved.name || '',
      email: saved.email || '',
      phone: saved.phone || '',
      education: saved.education || '',
      skills: saved.skills || '',
      experience: saved.experience || '',
      certifications: saved.certifications || '',
      projects: (saved.projects || []).map(p => ({
        name: p.name || '',
        description: p.description || '',
        technologies: p.technologies || ''
      }))
    });
    setJd(saved.jobDescription || '');
  };

  // Delete a saved candidate
  const handleDeleteSaved = async (id, e) => {
    e.stopPropagation();
    if (!window.confirm('Delete this saved candidate?')) return;
    try {
      await deleteCandidate(id);
      loadSavedCandidates();
    } catch (err) {
      alert('Delete failed');
    }
  };

  const handleRunAgent = async () => {
    try {
      const skillsArray = candidate.skills.split(',').map(s => s.trim()).filter(Boolean);
      const projectsArray = candidate.projects.map(p => ({
        name: p.name,
        description: p.description || '',
        technologies: typeof p.technologies === 'string'
          ? p.technologies.split(',').map(t => t.trim()).filter(Boolean)
          : p.technologies
      }));
      const payloadCandidate = {
        ...candidate,
        skills: skillsArray,
        projects: projectsArray,
        experiences: [],
        certifications: []
      };
      await runAgent(jd, payloadCandidate);
      navigate('/workflow');
    } catch (err) {
      console.error(err);
      alert('Error running agent: ' + (err.response?.data?.message || err.message));
    }
  };

  return (
    <div className="max-w-7xl mx-auto">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">New Application</h1>
        <div className="flex gap-2">
          <button onClick={handleLoadDemo} className="bg-gray-200 hover:bg-gray-300 text-gray-800 font-medium py-2 px-4 rounded transition">
            Load Demo Data
          </button>
          <button onClick={handleSaveCandidate} disabled={saving || !candidate.name}
            className="bg-green-600 hover:bg-green-700 disabled:bg-green-300 text-white font-medium py-2 px-4 rounded transition flex items-center gap-2">
            {saving ? '💾 Saving...' : '💾 Save to DB'}
          </button>
        </div>
      </div>
      {saveMsg && (
        <div className={`mb-4 p-3 rounded text-sm font-medium ${saveMsg.startsWith('✓') ? 'bg-green-100 text-green-800 border border-green-200' : 'bg-red-100 text-red-800 border border-red-200'}`}>
          {saveMsg}
        </div>
      )}

      {/* Saved Candidates Strip */}
      {savedCandidates.length > 0 && (
        <div className="mb-6 bg-white p-4 rounded-xl shadow-sm border border-gray-200">
          <h3 className="text-sm font-bold text-gray-500 uppercase tracking-wider mb-3">Saved Candidates (MySQL)</h3>
          <div className="flex gap-3 overflow-x-auto pb-2">
            {savedCandidates.map(sc => (
              <div key={sc.id} onClick={() => handleLoadSaved(sc)}
                className="flex-shrink-0 bg-gray-50 hover:bg-primary-50 border border-gray-200 hover:border-primary-300 rounded-lg p-3 cursor-pointer transition min-w-[180px]">
                <p className="font-medium text-sm">{sc.name}</p>
                <p className="text-xs text-gray-500 mt-1">{sc.education || 'No education'}</p>
                <p className="text-xs text-gray-400 mt-1">{sc.skills ? sc.skills.substring(0, 40) + '...' : ''}</p>
                <div className="flex justify-between items-center mt-2">
                  <span className="text-xs text-gray-400">ID: {sc.id}</span>
                  <button onClick={(e) => handleDeleteSaved(sc.id, e)}
                    className="text-xs text-red-500 hover:text-red-700">✕ Delete</button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      <div className="grid lg:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-200">
          <h2 className="text-xl font-bold mb-4">Job Description</h2>
          <textarea
            className="w-full h-[550px] p-4 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-primary-500 outline-none resize-none font-mono text-sm"
            placeholder="Paste job description here..."
            value={jd}
            onChange={(e) => setJd(e.target.value)}
          />
        </div>

        <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-200 overflow-y-auto h-[630px]">
          <h2 className="text-xl font-bold mb-4">Candidate Profile</h2>
          <div className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Full Name *</label>
              <input type="text" name="name" value={candidate.name} onChange={handleChange} className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" />
            </div>
            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                <input type="email" name="email" value={candidate.email} onChange={handleChange} className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Phone</label>
                <input type="text" name="phone" value={candidate.phone} onChange={handleChange} className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" />
              </div>
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Education</label>
              <input type="text" name="education" value={candidate.education} onChange={handleChange} className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Skills (comma separated)</label>
              <input type="text" name="skills" value={candidate.skills} onChange={handleChange} className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Experience</label>
              <textarea name="experience" value={candidate.experience} onChange={handleChange} className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" rows="2"></textarea>
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Certifications</label>
              <textarea name="certifications" value={candidate.certifications} onChange={handleChange} className="w-full p-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 outline-none" rows="2"></textarea>
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">Projects</label>
              {candidate.projects.map((proj, idx) => (
                <div key={idx} className="bg-gray-50 p-3 rounded mb-2 border border-gray-200">
                  <p className="font-medium text-sm">{proj.name}</p>
                  <p className="text-xs text-gray-600 mt-1">Tech: {proj.technologies}</p>
                </div>
              ))}
              {candidate.projects.length === 0 && <p className="text-sm text-gray-500 italic">No projects added.</p>}
            </div>
          </div>
        </div>
      </div>

      <div className="mt-8 flex justify-center gap-4">
        <button
          onClick={handleRunAgent}
          disabled={loading || !jd || !candidate.name}
          className="bg-primary-600 hover:bg-primary-700 disabled:bg-primary-300 text-white font-bold py-4 px-12 rounded-xl shadow-md transition text-lg flex items-center"
        >
          {loading ? <LoadingSpinner message="Running Agent..." /> : 'Run Agent ▶'}
        </button>
      </div>
    </div>
  );
}
