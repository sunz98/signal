// src/routes/MainRoutes.js
import React from 'react';
import { Route, Routes } from 'react-router-dom';
import HomePage from '../pages/HomePage';
import ArticlePage from '../pages/ArticlePage';
import MyPage from '../pages/MyPage';
import ConsultingPage from '../pages/ConsultingPage';

const MainRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/article" element={<ArticlePage />} />
      <Route path="/mypage" element={<MyPage />} />
      <Route path="/consulting" element={<ConsultingPage />} />
    </Routes>
  );
};

export default MainRoutes;
