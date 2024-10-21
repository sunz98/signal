import React from 'react';
import { Route, Routes } from 'react-router-dom';
import HomePage from '../pages/HomePage';
import ArticlePage from '../pages/ArticlePage';
import MyPage from '../pages/MyPage';
import ConsultingPage from '../pages/ConsultingPage';
<<<<<<< HEAD
import LoginPage from '../pages/Login';  // 로그인 페이지 import 추가
=======
import Login from "../pages/Login";
>>>>>>> 67068c1a5a146ac0dd0521e57b8f03f10e1a5513

const MainRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/article" element={<ArticlePage />} />
      <Route path="/mypage" element={<MyPage />} />
      <Route path="/consulting" element={<ConsultingPage />} />
      <Route path="/login" element={<Login />} /> {/* 로그인 페이지 경로 추가 */}
    </Routes>
  );
};

export default MainRoutes;
