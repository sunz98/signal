// src/components/Header.js
import React, {useState} from 'react';   //usestate는 로그인 여부 판별
import { Link } from 'react-router-dom';
import './Header.css';

const Header = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false); // 로그인 상태 관리

  const handleLoginLogout = () => {
    setIsLoggedIn(!isLoggedIn); // 로그인 상태를 토글
  };

  return (
    <header className="header">
      <div className='header-content'>
        <div className="logo">
          <Link to="/">
          <img src="/img/mainlogo.png" alt="Signal Logo" className="logo-image" />
          </Link>
        </div>
        
        <nav className="nav-links">
          <Link to="/article">Article</Link>
          <Link to="/community">Community</Link>
          <Link to="/consulting">Consulting</Link>

          {isLoggedIn ? (
            <>
              <Link to="/mypage">Mypage</Link>
              <button onClick={handleLoginLogout}>Logout</button>
            </>
          ) : (
            <button onClick={handleLoginLogout}>Login</button>
          )}
        </nav>
      </div>
    </header>
  );
};

export default Header;