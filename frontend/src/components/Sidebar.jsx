import React from 'react';
import { NavLink } from 'react-router-dom';

export default function Sidebar() {
  const navItems = [
    { path: '/', label: 'Dashboard', icon: '🏠' },
    { path: '/upload', label: 'Upload', icon: '📤' },
    { path: '/workflow', label: 'Workflow', icon: '⚡' },
    { path: '/jd-analysis', label: 'JD Analysis', icon: '📋' },
    { path: '/evidence', label: 'Evidence', icon: '🔍' },
    { path: '/evaluation', label: 'Evaluation', icon: '📊' },
    { path: '/resume', label: 'Resume', icon: '📄' },
    { path: '/report', label: 'Report', icon: '📝' },
  ];

  return (
    <div className="w-64 bg-slate-900 text-white flex-shrink-0 hidden md:flex flex-col">
      <div className="h-16 flex items-center px-6 border-b border-slate-800">
        <h1 className="font-bold text-xl flex items-center gap-2">
          <span className="text-2xl">🤖</span> ApplyAgent
        </h1>
      </div>
      <nav className="flex-1 overflow-y-auto py-4">
        <ul className="space-y-1 px-3">
          {navItems.map((item) => (
            <li key={item.path}>
              <NavLink
                to={item.path}
                className={({ isActive }) => 
                  `flex items-center gap-3 px-3 py-2 rounded-lg transition-colors ${
                    isActive ? 'bg-primary-600 text-white' : 'text-slate-300 hover:bg-slate-800 hover:text-white'
                  }`
                }
              >
                <span className="text-lg">{item.icon}</span>
                <span className="font-medium">{item.label}</span>
              </NavLink>
            </li>
          ))}
        </ul>
      </nav>
      <div className="p-4 border-t border-slate-800">
        <div className="text-xs text-slate-500 text-center">
          Hackathon Prototype
        </div>
      </div>
    </div>
  );
}
