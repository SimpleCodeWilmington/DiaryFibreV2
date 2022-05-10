import React, { useEffect } from 'react';
import { Translate, TextFormat } from 'react-jhipster';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import moment from 'moment';
import { sortCommentsByDate } from '../../modules/sortCommentsByDate';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/blog-comment/blog-comment.reducer';
import AddComment from './comment-button';
import { Col, Row } from 'reactstrap';

export const Comments = () => {
  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);
  const blogCommentList = useAppSelector(state => state.blogComment.entities);
  const loading = useAppSelector(state => state.blogComment.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1>
            <Translate contentKey="diaryFibreApp.blogComment.home.title">Create or edit a BlogComment</Translate>
          </h1>
        </Col>
      </Row>
      <div className="table-responsive">
        <Row className="justify-content-center">
          <Col md="8">
            {blogCommentList && blogCommentList.length > 0 ? (
              <div>
                {sortCommentsByDate(blogCommentList).map((comments, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <li className="list-group-item pl-0" key={comments._id} >
                      <p className="text-muted mb-1" >
                        Posted by {comments.user ? comments.user.firstName : ''} on{' '}
                        {comments.dateTime ? <TextFormat type="date" value={comments.dateTime} format={APP_DATE_FORMAT} /> : null}
                      </p>
                      <p className="mb-1">{comments.comment}</p>
                    </li>
                  </tr>
                ))}
              </div>
            ) : (
              !loading && (
                <div className="alert alert-warning">
                  <Translate contentKey="diaryFibreApp.blogComment.home.notFound">No Blog Comments found</Translate>
                </div>
              )
            )}
          </Col>
        </Row>
      </div>
      <div className="comment-button">
        <Row className="justify-content-center">
          <Col md="8">
            <AddComment />
          </Col>
        </Row>
      </div>
    </div>
  );
};

export default Comments;
