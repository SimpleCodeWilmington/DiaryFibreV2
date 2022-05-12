import React, { useEffect } from 'react';
import { Translate, TextFormat } from 'react-jhipster';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities } from 'app/entities/blog-comment/blog-comment.reducer';
import { Col, Row } from 'reactstrap';
import { Button, Table } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link, RouteComponentProps } from 'react-router-dom';
import EditComment from './edit-comment-button';
import DeleteComment from './delete-comment-button';

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
            <Translate contentKey="diaryFibreApp.blogComment.home.title">Comments</Translate>
          </h1>
        </Col>
      </Row>
      <div className="table-responsive">
        <Row className="justify-content-center">
          <Col md="8">
            {blogCommentList && blogCommentList.length > 0 ? (
               <Table responsive>
                 <tbody>
                {(blogCommentList).map((comments, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <li className="list-group-item pl-0" key={comments._id} >
                      <p className="text-muted mb-1" >
                        Posted by {comments.user ? comments.user.login : ''} on{' '}
                        {comments.dateTime ? <TextFormat type="date" value={comments.dateTime} format={APP_DATE_FORMAT} /> : null}
                      </p>
                      <p className="mb-1">{comments.comment}</p>

                    <div className="btn-group flex-btn-group-container">
                    <Link to={`/blog-comment/${comments.id}/edit`}>
                        <EditComment/>
                        </Link>
                      </div>

                      <div className="btn-group flex-btn-group-container">
                    <Link to={`/blog-comment/${comments.id}/delete`}>
                        <DeleteComment/>
                        </Link>
                        </div>

                      </li>
                  </tr>
                ))}
                </tbody>
          </Table>
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

    </div>
  );
};

export default Comments;
