// src/pages/PostDetailPage.js
import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './PostDetailPage.css';

const PostDetailPage = () => {
  const { postId } = useParams();
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPost = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/post/${postId}`);
        if (!response.ok) throw new Error('게시글을 가져오는데 실패했습니다.');
        const data = await response.json();
        setPost(data); // 게시글 데이터 설정
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false); // 로딩 완료
      }
    };

    fetchPost();
  }, [postId]);

  if (loading) return <p>로딩 중...</p>;
  if (error) return <p>오류 발생: {error}</p>;

  return (
    <div className="post-detail-page">
      <button onClick={() => navigate(-1)} className="back-button">
        &larr; 뒤로가기
      </button>

      <h1>{post.title}</h1>
      <div className="post-meta">
        <p>작성일: {new Date(post.createdAt).toLocaleDateString()}</p>
        <p>조회수: {post.viewCount}</p>
        <p>좋아요: {post.likesCount}</p>
      </div>

      <div className="post-content">
        <p>{post.contents}</p>
      </div>
    </div>
  );
};

export default PostDetailPage;
