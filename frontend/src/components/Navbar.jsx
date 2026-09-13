import React from 'react';

export default function Navbar() {
  return (
    <header className="bg-white border-b border-gray-200 h-16 flex items-center px-6 justify-between shrink-0">
      <div className="font-bold text-xl text-primary-700 flex items-center gap-2 md:hidden">
        <span className="text-2xl">🤖</span> ApplyAgent
      </div>
      <div className="hidden md:block"></div> {/* Spacer for desktop where sidebar has logo */}
      <div className="flex items-center gap-4">
        <span className="text-sm text-gray-500 bg-gray-100 px-3 py-1 rounded-full font-medium">Demo Mode</span>
      </div>
    </header>
  );
}
