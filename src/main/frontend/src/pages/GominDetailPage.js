import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom'; // useNavigate 추가
import CommunityNavigation from '../components/CommunityNavigation'; // 네비게이션 컴포넌트 추가
import './GominDetailPage.css';  // 스타일 파일

// Mock 데이터
const mockData = {
  totalPages: 5,
  currentPage: 0,
  content: {
    totalCount: 14,
    hotPost: {
      id: 1,
      title: "데이트 비용과 관련해서 제가 잘못한 건가요..??",
      createdAt: "2024-09-28",
      category: "20s",
      viewCount: 145,
      likeCount: 24,
      commentCount: 13
    },
    posts: [
      {
        id: 1,
        title: "취업이 다가오니까 여러가지 문제가 생겨요",
        createdAt: "2024-09-28",
        category: "20s",
        viewCount: 45,
        likeCount: 4,
        commentCount: 5
      },
      {
        id: 2,
        title: "롱디 선배님들, 조언을 좀 구하고 싶습니다!",
        createdAt: "2024-09-29",
        category: "20s",
        viewCount: 13,
        likeCount: 5,
        commentCount: 2
      },
      {
        id: 3,
        title: "여자친구와 저의 성향이 너무 달라요...",
        createdAt: "2024-09-29",
        category: "20s",
        viewCount: 11,
        likeCount: 2,
        commentCount: 3
      },
      {
        id: 4,
        title: "안녕하세요 고민있습니다.",
        createdAt: "2024-10-01",
        category: "20s",
        viewCount: 13,
        likeCount: 5,
        commentCount: 3
      },
      {
        id: 5,
        title: "비밀연애가 진짜 힘드네요..",
        createdAt: "2024-10-03",
        category: "20s",
        viewCount: 23,
        likeCount: 1,
        commentCount: 3
      },
      {
        id: 6,
        title: "남자친구가 게임을 너무 많이 해요",
        createdAt: "2024-10-07",
        category: "20s",
        viewCount: 3,
        likeCount: 1,
        commentCount: 1
      },
      {
        id: 7,
        title: "다른 대학교 커플 어떻게 만나시나요?",
        createdAt: "2024-10-07",
        category: "20s",
        viewCount: 13,
        likeCount: 4,
        commentCount: 2
      },
      {
        id: 8,
        title: "남자친구를 보기 너무 힘들어요ㅠㅠ",
        createdAt: "2024-10-09",
        category: "20s",
        viewCount: 3,
        likeCount: 0,
        commentCount: 0
      }
    ]
  }
};

const GominDetailPage = () => {
  const { category } = useParams();  // URL에서 카테고리 파라미터 가져오기
  const [posts, setPosts] = useState([]);  // 게시글 데이터를 저장할 상태
  const [hotPost, setHotPost] = useState(null);  // 핫 게시글 저장 상태
  const [pagination, setPagination] = useState({ totalPages: 5, currentPage: 0 });  // 페이지네이션 상태

  const navigate = useNavigate();  // 페이지 이동을 위한 useNavigate 사용

  // 페이지가 로드될 때 Mock 데이터를 사용하여 테스트
  useEffect(() => {
    const fetchMockData = () => {
      const data = mockData;  // 실제 API 대신 mock 데이터를 사용
      setPosts(data.content.posts);  // 게시글 데이터 설정
      setHotPost(data.content.hotPost);  // 핫 게시글 설정
      setPagination({ totalPages: data.totalPages, currentPage: data.currentPage });

      // 작성된 게시글이 있으면 추가
      if (window.location.state && window.location.state.newPost) {
        setPosts((prevPosts) => [window.location.state.newPost, ...prevPosts]);
      }
    };

    fetchMockData();  // 페이지 로드 시 mock 데이터 로딩
  }, [category, window.location.state]);  // location.state로 추가된 게시글 감지

  // 페이지 변경 함수
  const handlePageChange = (newPage) => {
    setPagination((prev) => ({ ...prev, currentPage: newPage }));
  };

  // 게시글 클릭 시 상세 페이지로 이동
  const handlePostClick = (postId) => {
    navigate(`/post/${postId}`);  // 게시글 상세 페이지로 이동
  };

  // 페이지네이션을 계산하는 함수
  const generatePagination = () => {
    const totalPages = pagination.totalPages;
    const currentPage = pagination.currentPage;

    let pages = [];

    // 항상 이전 버튼
    pages.push(
      <button key="prev" onClick={() => handlePageChange(Math.max(currentPage - 1, 0))} className="prev">
        &larr; 이전
      </button>
    );

    // 페이지가 6개 이하일 때는 모든 페이지를 표시
    if (totalPages <= 6) {
      for (let i = 0; i < totalPages; i++) {
        pages.push(
          <button
            key={i}
            onClick={() => handlePageChange(i)}
            className={currentPage === i ? 'active' : ''}
          >
            {i + 1}
          </button>
        );
      }
    } else {
      // 7페이지 이상일 때는 일부만 표시
      pages.push(
        <button key={0} onClick={() => handlePageChange(0)} className={currentPage === 0 ? 'active' : ''}>
          1
        </button>
      );

      if (currentPage > 2) {
        pages.push(<span key="start-dots">...</span>);
      }

      const startPage = Math.max(1, currentPage - 1);
      const endPage = Math.min(totalPages - 2, currentPage + 1);

      for (let i = startPage; i <= endPage; i++) {
        pages.push(
          <button
            key={i}
            onClick={() => handlePageChange(i)}
            className={currentPage === i ? 'active' : ''}
          >
            {i + 1}
          </button>
        );
      }

      if (currentPage < totalPages - 3) {
        pages.push(<span key="end-dots">...</span>);
      }

      pages.push(
        <button
          key={totalPages - 1}
          onClick={() => handlePageChange(totalPages - 1)}
          className={currentPage === totalPages - 1 ? 'active' : ''}
        >
          {totalPages}
        </button>
      );
    }

    // 항상 다음 버튼
    pages.push(
      <button key="next" onClick={() => handlePageChange(Math.min(currentPage + 1, totalPages - 1))} className="next">
        다음 &rarr;
      </button>
    );

    return pages;
  };

  // 게시글 작성 페이지로 이동하는 함수
  const handleWritePost = () => {
    navigate('/community/write');  // 게시글 작성 페이지로 이동
  };

  return (
    <div className="gomin-detail-page">
      <CommunityNavigation />  {/* 네비게이션 추가 */}

      <div className="gomin-content">
        <h2>고민 / {category} 대</h2>

        {/* 게시글 목록 테이블 */}
        <table className="post-list-table">
          <thead>
            <tr>
              <th>제목</th>
              <th>작성일시</th>
              <th>조회수</th>
              <th>좋아요</th>
            </tr>
          </thead>

          <tbody>
            {/* 핫 게시글 표시 */}
            {hotPost && (
              <tr className="hot-post" onClick={() => handlePostClick(hotPost.id)}>
                <td>{hotPost.title}</td>
                <td>{hotPost.createdAt}</td>
                <td>{hotPost.viewCount}</td>
                <td>{hotPost.likeCount}</td>
              </tr>
            )}

            {/* 일반 게시글 목록 */}
            {posts.length === 0 ? (
              <tr>
                <td colSpan="4">게시글이 없습니다.</td>
              </tr>
            ) : (
              posts.map((post) => (
                <tr key={post.id} onClick={() => handlePostClick(post.id)}>
                  <td>{post.title}</td>
                  <td>{post.createdAt}</td>
                  <td>{post.viewCount}</td>
                  <td>{post.likeCount}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>

        {/* 페이지네이션 및 작성하기 버튼 */}
        <div className="pagination-container">
          <div className="pagination">
            {generatePagination()}
          </div>
          <button className="write-post-button" onClick={handleWritePost}>
            작성하기
          </button>
        </div>
      </div>
    </div>
  );
};

export default GominDetailPage;
