import React, { createContext, useState, useContext } from 'react';
import { runAgent as apiRunAgent } from '../api';

const AgentContext = createContext();

export const useAgent = () => useContext(AgentContext);

export const AgentProvider = ({ children }) => {
  const [agentResult, setAgentResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const runAgent = async (jobDescription, candidate) => {
    setLoading(true);
    setError(null);
    try {
      const response = await apiRunAgent(jobDescription, candidate);
      const data = response.data;
      const result = data.result || data;
      setAgentResult(result);
      return result;
    } catch (err) {
      console.error("Error running agent:", err);
      setError(err.message || 'An error occurred running the agent');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  return (
    <AgentContext.Provider value={{ agentResult, setAgentResult, loading, error, runAgent }}>
      {children}
    </AgentContext.Provider>
  );
};
