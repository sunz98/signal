import React, { useState } from 'react';

const Login = () => {
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');

  return (
      <div>
        <h2>Login Page</h2>
        <hr />
        <form action="http://localhost:8080/login" method="POST">  {/* Spring Security의 loginProcessingUrl과 일치 */}
          <input
              id="userId"
              type="text"
              name="userId"  // Spring Security의 기본 필드 이름과 일치시켜야 함 (username, password)
              placeholder="id"
              value={userId}
              onChange={(e) => setUserId(e.target.value)} // 상태 업데이트
          />
          <input
              id="password"
              type="password"
              name="password"  // Spring Security의 기본 필드 이름과 일치
              placeholder="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)} // 상태 업데이트
          />
          <input type="submit" value="login"/>
        </form>
      </div>
  );
};

export default Login;
