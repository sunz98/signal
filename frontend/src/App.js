// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import CommunityPage from './pages/CommunityPage';
import GominDetailPage from './pages/GominDetailPage';
import InfoDetailPage from './pages/InfoDetailPage';
import WritePage from './pages/WritePage';
import PostDetailPage from './pages/PostDetailPage';
import MainPage from './pages/HomePage'; // 메인 페이지
import HomePage from './pages/HomePage';

function App() {
  return (
    <Router>
      <div className="app-container">
        <Header />
        <main className="content">
          <Routes>
            {/* 메인 페이지 */}
            <Route path="/" element={<HomePage />} />

            {/* 커뮤니티 관련 경로 */}
            <Route path="/community" element={<CommunityPage />} />
            <Route path="/community/gomin/:category" element={<GominDetailPage />} />
            <Route path="/community/info/:category" element={<InfoDetailPage />} />
            <Route path="/community/write" element={<WritePage />} />

            {/* 게시글 상세 페이지 */}
            <Route path="/post/:postId" element={<PostDetailPage />} />

            {/* 404 페이지 */}
            <Route path="*" element={<div>페이지를 찾을 수 없습니다.</div>} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
