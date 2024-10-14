import React from 'react';
import './HeroSlider.css';

const HeroSlider = () => {
  return (
    <div className="hero-slider">
      <div className="slide">
        <img src="/images/slider1.jpg" alt="배너 1" />
      </div>
      <div className="slide">
        <img src="/images/slider2.jpg" alt="배너 2" />
      </div>
    </div>
  );
};

export default HeroSlider;
