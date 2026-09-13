import React from 'react';

export default function ScoreCard({ label, score, max = 100 }) {
  const percentage = (score / max) * 100;
  
  let colorClass = 'text-green-600';
  let bgClass = 'bg-green-600';
  
  if (percentage < 50) {
    colorClass = 'text-red-600';
    bgClass = 'bg-red-600';
  } else if (percentage < 80) {
    colorClass = 'text-amber-500';
    bgClass = 'bg-amber-500';
  }

  return (
    <div className="bg-white p-4 rounded-xl shadow-sm border border-gray-200">
      <p className="text-sm text-gray-500 mb-1">{label}</p>
      <div className="flex items-end gap-2 mb-2">
        <span className={`text-2xl font-bold ${colorClass}`}>{score}</span>
        <span className="text-gray-400 text-sm mb-1">/ {max}</span>
      </div>
      <div className="w-full bg-gray-100 rounded-full h-2">
        <div className={`${bgClass} h-2 rounded-full`} style={{ width: `${percentage}%` }}></div>
      </div>
    </div>
  );
}
