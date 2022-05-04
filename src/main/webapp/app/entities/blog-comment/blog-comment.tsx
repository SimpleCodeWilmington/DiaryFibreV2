import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import moment from 'moment';
import { sortCommentsByDate } from '../../modules/sortCommentsByDate';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import AddComment from '../../modules/comments/add-comment';
import { IBlogComment } from 'app/shared/model/blog-comment.model';
import { getEntities } from './blog-comment.reducer';

export const BlogComment = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);
  const blogCommentList = useAppSelector(state => state.blogComment.entities);
  const loading = useAppSelector(state => state.blogComment.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

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
