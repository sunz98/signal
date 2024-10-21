import React, { useState } from 'react';
import './Login.css'; // 외부 스타일 시트로 분리

const Login = () => {
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');

  return (
    <div className="login-container">
      <div className="logo">
        <h1>Signal</h1> {/* 로고와 텍스트를 포함 */}
      </div>
      <form className="login-form" action="http://localhost:8080/login" method="POST">
        <input
          id="userId"
          type="text"
          name="userId"
          placeholder="아이디를 입력해주세요."
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
        />
        <input
          id="password"
          type="password"
          name="password"
          placeholder="비밀번호를 입력해주세요."
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit" className="login-button">로그인</button>
      </form>
      <div className="login-links">
        <a href="/register">회원가입</a>
        <a href="/find-id">아이디 찾기</a>
        <a href="/find-password">비밀번호 찾기</a>
      </div>
    </div>
  );
};

export default Login;
