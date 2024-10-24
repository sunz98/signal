import React, { useState } from 'react';
import './SignUp.css';

const SignUp = () => {
  const [formData, setFormData] = useState({
    userId: '',
    password: '',
    confirmPassword: '',
    nickname: '',
    birthYear: '',
    birthMonth: '',
    birthDay: '',
    gender: '',
    email: '',
    emailCode: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // 회원가입 로직 추가
    console.log(formData);
  };

  return (
    <div className="signup-container">
      <div className="logo">
        <img src="/img/loginLogo.png" alt="Signal Logo" /> {/* 로고 이미지 */}
      </div>
      <form className="signup-form" onSubmit={handleSubmit}>
        <input
          type="text"
          name="userId"
          placeholder="아이디를 입력해주세요."
          value={formData.userId}
          onChange={handleChange}
        />
        <input
          type="password"
          name="password"
          placeholder="비밀번호를 입력해주세요."
          value={formData.password}
          onChange={handleChange}
        />
        <input
          type="password"
          name="confirmPassword"
          placeholder="비밀번호를 다시 한 번 입력해주세요."
          value={formData.confirmPassword}
          onChange={handleChange}
        />
        <input
          type="text"
          name="nickname"
          placeholder="닉네임을 입력해주세요."
          value={formData.nickname}
          onChange={handleChange}
        />

        <div className="birthdate-section">
          <select
            name="birthYear"
            value={formData.birthYear}
            onChange={handleChange}
          >
            <option value="">연도</option>
            {/* 연도 옵션 추가 */}
          </select>
          <select
            name="birthMonth"
            value={formData.birthMonth}
            onChange={handleChange}
          >
            <option value="">월</option>
            {/* 월 옵션 추가 */}
          </select>
          <select
            name="birthDay"
            value={formData.birthDay}
            onChange={handleChange}
          >
            <option value="">일</option>
            {/* 일 옵션 추가 */}
          </select>
        </div>

        <div className="gender-section">
          <select name="gender" value={formData.gender} onChange={handleChange}>
            <option value="">성별</option>
            <option value="male">남성</option>
            <option value="female">여성</option>
          </select>
        </div>

        <input
          type="email"
          name="email"
          placeholder="본인 확인을 위한 이메일을 입력해주세요."
          value={formData.email}
          onChange={handleChange}
        />
        <button type="button" className="email-verify-button">인증</button>
        <input
          type="text"
          name="emailCode"
          placeholder="이메일 인증 번호를 입력해주세요."
          value={formData.emailCode}
          onChange={handleChange}
        />

        <button type="submit" className="signup-button">회원가입</button>
      </form>
    </div>
  );
};

export default SignUp;
