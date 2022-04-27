import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBlogText } from 'app/shared/model/blog-text.model';
import { getEntities } from './blog-text.reducer';

export const BlogText = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const blogTextList = useAppSelector(state => state.blogText.entities);
  const loading = useAppSelector(state => state.blogText.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="blog-text-heading" data-cy="BlogTextHeading">
        <Translate contentKey="diaryFibreApp.blogText.home.title">Blog Texts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="diaryFibreApp.blogText.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/blog-text/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="diaryFibreApp.blogText.home.createLabel">Create new Blog Text</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {blogTextList && blogTextList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="diaryFibreApp.blogText.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="diaryFibreApp.blogText.blogPostID">Blog Post ID</Translate>
                </th>
                <th>
                  <Translate contentKey="diaryFibreApp.blogText.blogText">Blog Text</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {blogTextList.map((blogText, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/blog-text/${blogText.id}`} color="link" size="sm">
                      {blogText.id}
                    </Button>
                  </td>
                  <td>{blogText.blogPostID}</td>
                  <td>
                    {blogText.blogText ? (
                      <div>
                        {blogText.blogTextContentType ? (
                          <a onClick={openFile(blogText.blogTextContentType, blogText.blogText)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {blogText.blogTextContentType}, {byteSize(blogText.blogText)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/blog-text/${blogText.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/blog-text/${blogText.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/blog-text/${blogText.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="diaryFibreApp.blogText.home.notFound">No Blog Texts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BlogText;
