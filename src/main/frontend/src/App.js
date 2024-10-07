// src/App.js
import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import MainRoutes from './routes/MainRoutes'; // 메인 라우트 모듈
import CommunityRoutes from './routes/CommunityRoutes'; // 커뮤니티 라우트 모듈


function App() {
  return (
    <Router>
      <div className="app-container">
        <Header />
        <main className="content">
          <MainRoutes />
          <CommunityRoutes />
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
