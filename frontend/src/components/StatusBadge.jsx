import React from 'react';

export default function StatusBadge({ status, text }) {
  const styles = {
    SUCCESS: 'bg-green-100 text-green-800 border-green-200',
    WARNING: 'bg-amber-100 text-amber-800 border-amber-200',
    ERROR: 'bg-red-100 text-red-800 border-red-200',
    INFO: 'bg-blue-100 text-blue-800 border-blue-200',
  };

  const className = styles[status] || styles.INFO;

  return (
    <span className={`px-2 py-1 rounded text-xs font-bold border uppercase tracking-wider ${className}`}>
      {text || status}
    </span>
  );
}
