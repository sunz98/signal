import React from 'react';
import './ConsultingSection.css';

const consultants = [
  { id: 1, name: '상담사 1', image: '/images/consultant1.jpg' },
  { id: 2, name: '상담사 2', image: '/images/consultant2.jpg' },
];

const ConsultingSection = () => {
  return (
    <div className="consulting-section">
      {consultants.map((consultant) => (
        <div key={consultant.id} className="consultant">
          <img src={consultant.image} alt={consultant.name} />
          <span>{consultant.name}</span>
        </div>
      ))}
    </div>
  );
};

export default ConsultingSection;
