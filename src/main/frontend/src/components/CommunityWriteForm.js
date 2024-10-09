import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './CommunityWriteForm.css';

const CommunityWriteForm = () => {
  const [category, setCategory] = useState('30');
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [invalidSentences, setInvalidSentences] = useState(null); // 문제 있는 문장을 저장할 상태
  const [filtered, setfiltered] = useState(false); // 필터링 여부 상태

  const navigate = useNavigate();  //페이지 이동 훅

  const handleSubmit = async (e) => {
    e.preventDefault(); // 새로고침 방지
  
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
  
      // 응답이 제대로 오는지 확인하기 위한 로그 출력
      console.log("Response status:", response.status);
      const result = await response.json();
  
      // 응답 데이터를 확인하기 위한 로그 출력
      console.log("Result:", result);
  
      // ChatGPT 필터링 결과 처리
      if (result.filtered !== undefined) { // isFiltered가 정의되어 있는지 확인
        if (result.filtered) {
          console.log("필터링된 문장:", result.invalidSentences); // 문제가 있는 문장을 확인하는 로그
          setInvalidSentences(result.invalidSentences); // 문제가 있는 문장 저장
          setfiltered(true); // 필터링 여부 true로 설정
  
          return; // 필터링되었으므로 페이지 이동 막음
        } else {
          console.log('게시글 작성 성공!'); // 게시글 작성 성공 메시지
          navigate(`/community/gomin/${category}`); // 작성 후 페이지 이동
        }
      } else {
        console.error('isFiltered 값이 응답에 없습니다.');
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
        return 'MILITARY';
      case 'CC':
        return 'CC';
      case 'office':
        return 'OFFICE';
      case 'date-places':
        return 'DATE_LOCATION';
      case 'gifts':
        return 'ANNIVERSARY_GIFT';
      case 'travel':
        return 'TRAVEL_LOCATION';
      default:
        return category;
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
        className={`content-textarea ${filtered ? 'error-content' : ''}`} // 필터링된 경우 스타일 적용
        value={content}
        onChange={(e) => setContent(e.target.value)}
        placeholder="내용"
        spellCheck="false"
      ></textarea>

{filtered && invalidSentences && (
  <div className="error-message">
    <p>다음 문장을 수정해주세요:</p>
    <div className="invalid-sentences">
      {invalidSentences.map((sentence, index) => (
        <p key={index}>{sentence}</p>  // 문장마다 줄바꿈
      ))}
    </div>
  </div>
)}

      <div className="form-footer">
        <button type="submit" className="submit-button">
          작성하기
        </button>
      </div>
    </form>
  );
};

export default CommunityWriteForm;
