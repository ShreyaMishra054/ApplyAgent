import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
});

// Existing agent workflow APIs
export const analyzeJd = (jobDescription) => api.post('/analyze-jd', { jobDescription });
export const analyzeCandidate = (candidate) => api.post('/analyze-candidate', candidate);
export const matchEvidence = (jobDescription, candidate) => api.post('/match-evidence', { jobDescription, candidate });
export const generateResume = (data) => api.post('/generate-resume', data);
export const evaluateResume = (resume) => api.post('/evaluate', resume);
export const reviseResume = (data) => api.post('/revise', data);
export const verifyResume = (data) => api.post('/verify', data);
export const runAgent = (jobDescription, candidate) => api.post('/run-agent', { jobDescription, candidate });
export const getDownloadUrl = (fileName) => `${API_BASE_URL}/download-resume?fileName=${fileName}`;

// NEW: Candidate persistence APIs (MySQL)
export const getSavedCandidates = () => api.get('/candidates');
export const getSavedCandidateById = (id) => api.get(`/candidates/${id}`);
export const saveCandidate = (candidateData) => api.post('/candidates', candidateData);
export const updateCandidate = (id, candidateData) => api.put(`/candidates/${id}`, candidateData);
export const deleteCandidate = (id) => api.delete(`/candidates/${id}`);

export default api;
