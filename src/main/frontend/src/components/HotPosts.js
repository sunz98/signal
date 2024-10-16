import React from 'react';
import { useNavigate } from 'react-router-dom';
import './HotPosts.css';

const HotPosts = ({ posts }) => {
  const navigate = useNavigate();

  return (
    <div className="hot-posts">
      {posts.map((post) => (
        <div
          key={post.id}
          className="hot-post"
          onClick={() => navigate(`/post/${post.id}`)}
        >
          <h3>{post.title}</h3>
          <p>{post.createdAt}</p>
        </div>
      ))}
    </div>
  );
};

export default HotPosts;
