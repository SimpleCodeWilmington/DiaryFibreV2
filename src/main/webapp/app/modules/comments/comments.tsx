import React, { useEffect } from 'react';
import { Translate } from 'react-jhipster';
import moment from 'moment';
import { sortCommentsByDate } from '../../modules/sortCommentsByDate';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/blog-comment/blog-comment.reducer';
import AddComment from './add-comment';

export const BlogComment = () => {
  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);
  const blogCommentList = useAppSelector(state => state.blogComment.entities);
  const loading = useAppSelector(state => state.blogComment.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  return (
    <div>
      <AddComment />
      <br></br>
      <br></br>
      <div className="table-responsive">
        {blogCommentList && blogCommentList.length > 0 ? (
          <div>
            {sortCommentsByDate(blogCommentList).map((blogComment, i) => (
              <li className="list-group-item pl-0" key={blogComment._id}>
                <p className="text-muted mb-1">
                  Posted by {account.firstName} on {moment(blogComment.dateTime).format('MM-DD-YY [at] HH:mm')}
                </p>
                <p className="mb-1">{blogComment.comment}</p>
              </li>
            ))}
          </div>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="diaryFibreApp.blogComment.home.notFound">No Blog Comments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BlogComment;
