import React from 'react';


import AddComment from './comment-button';
import Comments from './comments';

export const CommentsPage = () => {
  return (
    <div>
          <AddComment />
          <br></br>
          <br></br>
          <Comments />

    </div>
  );
};

export default CommentsPage;
