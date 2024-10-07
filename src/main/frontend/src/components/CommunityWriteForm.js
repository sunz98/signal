import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // useNavigate 추가
import './CommunityWriteForm.css';

const CommunityWriteForm = () => {
  const [category, setCategory] = useState('20대 고민');
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const navigate = useNavigate(); // 페이지 이동을 위한 navigate 사용

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    const requestBody = {
      title: title,
      contents: content,
      category: mapCategoryToBackendFormat(category),
    };
  
    try {
      const response = await fetch('http://localhost:8080/api/user/post', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'accept': '*/*',
        },
        body: JSON.stringify(requestBody),
      });
  
      // API 요청 후 상태 확인
      const result = await response.json();
      console.log(result); // 결과 출력
  
      if (response.ok) {
        console.log('게시글 작성 성공!');
        navigate(`/community/gomin/${category}`); // 작성 후 페이지 이동
      } else {
        console.error('게시글 작성 실패:', response.status, response.statusText);
      }
    } catch (error) {
      console.error('서버와의 통신 중 오류 발생:', error);
    }
  };

  // 카테고리 값을 백엔드 API에서 받을 수 있는 형식으로 변환
  const mapCategoryToBackendFormat = (category) => {
    switch (category) {
      case '10':
        return '_10S';
      case '20':
        return '_20S';
      case '30':
        return '_30S';
      case '40':
        return '_40S';
      case 'gomsin':
        return '_GOMSIN';
      case 'CC':
        return '_CC';
      case 'office':
        return '_OFFICE';
      case 'date-places':
        return '_DATE_PLACES';
      case 'gifts':
        return '_GIFTS';
      case 'travel':
        return '_TRAVEL';
      default:
        return '_UNKNOWN';
    }
  };

  return (
    <form className="write-form" onSubmit={handleSubmit}>
      <div className="form-header">
        <button className="back-button" onClick={() => window.history.back()}>
          &larr;
        </button>
        <select
          value={category}
          onChange={(e) => setCategory(e.target.value)}
          className="category-select"
        >
          <option value="10">10대 고민</option>
          <option value="20">20대 고민</option>
          <option value="30">30대 고민</option>
          <option value="40">40대 고민</option>
          <option value="gomsin">곰신 고민</option>
          <option value="CC">CC 고민</option>
          <option value="office">사내연애 고민</option>
          <option value="date-places">데이트 장소 추천</option>
          <option value="gifts">기념일 선물 추천</option>
          <option value="travel">커플 여행지 추천</option>
        </select>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          className="title-input"
          placeholder="제목"
        />
      </div>
      <textarea
        className="content-textarea"
        value={content}
        onChange={(e) => setContent(e.target.value)}
        placeholder="내용"
      ></textarea>
      <div className="form-footer">
        <button type="submit" className="submit-button">
          작성하기
        </button>
      </div>
    </form>
  );
};

export default CommunityWriteForm;
