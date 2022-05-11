import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBlogComment } from 'app/shared/model/blog-comment.model';
import { getEntities } from './blog-comment.reducer';

export const BlogComment = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

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
      <h2 id="blog-comment-heading" data-cy="BlogCommentHeading">
        <Translate contentKey="diaryFibreApp.blogComment.home.title">Blog Comments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="diaryFibreApp.blogComment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/blog-comment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="diaryFibreApp.blogComment.home.createLabel">Create new Blog Comment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {blogCommentList && blogCommentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="diaryFibreApp.blogComment.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="diaryFibreApp.blogComment.comment">Comment</Translate>
                </th>
                <th>
                  <Translate contentKey="diaryFibreApp.blogComment.dateTime">Date Time</Translate>
                </th>
                <th>
                  <Translate contentKey="diaryFibreApp.blogComment.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="diaryFibreApp.blogComment.blogPost">Blog Post</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {blogCommentList.map((blogComment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/blog-comment/${blogComment.id}`} color="link" size="sm">
                      {blogComment.id}
                    </Button>
                  </td>
                  <td>{blogComment.comment}</td>
                  <td>{blogComment.dateTime ? <TextFormat type="date" value={blogComment.dateTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{blogComment.user ? blogComment.user.id : ''}</td>
                  <td>{blogComment.blogPost ? <Link to={`/blog-post/${blogComment.blogPost.id}`}>{blogComment.blogPost.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/blog-comment/${blogComment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/blog-comment/${blogComment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/blog-comment/${blogComment.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
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
      </div>
    </div>
  );
};

export default BlogComment;
