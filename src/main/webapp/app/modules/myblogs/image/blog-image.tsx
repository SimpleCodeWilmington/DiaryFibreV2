import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBlogImage } from 'app/shared/model/blog-image.model';
import { getEntities } from './blog-image.reducer';

export const BlogImage = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const blogImageList = useAppSelector(state => state.blogImage.entities);
  const loadingImages = useAppSelector(state => state.blogImage.loadingImages);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      {/* <h2 id="blog-image-heading" data-cy="BlogImageHeading">
        <Translate contentKey="diaryFibreApp.blogImage.home.title">Blog Images</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loadingImages}>
            <FontAwesomeIcon icon="sync" spin={loadingImages} />{' '}
            <Translate contentKey="diaryFibreApp.blogImage.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/blog-image/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="diaryFibreApp.blogImage.home.createLabel">Create new Blog Image</Translate>
          </Link>
        </div>
      </h2> */}
      <div className="table-responsive">
        {blogImageList && blogImageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="diaryFibreApp.blogImage.blogImage">Blog Image</Translate>
                </th>
                <th>
                  <Translate contentKey="diaryFibreApp.blogImage.blogpost">Blogpost</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {blogImageList.map((blogImage, j) => (
                <tr key={`entity-${j}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/blog-image/${blogImage.id}`} color="link" size="sm">
                      {blogImage.id}
                    </Button>
                  </td>
                  <td>
                    {blogImage.blogImage ? (
                      <div>
                        {blogImage.blogImageContentType ? (
                          <a onClick={openFile(blogImage.blogImageContentType, blogImage.blogImage)}>
                            <img
                              src={`data:${blogImage.blogImageContentType};base64,${blogImage.blogImage}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {blogImage.blogImageContentType}, {byteSize(blogImage.blogImage)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{blogImage.imageNumber}</td>
                  <td>{blogImage.blogpost ? <Link to={`/blog-post/${blogImage.blogpost.id}`}>{blogImage.blogpost.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/blog-image/${blogImage.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/blog-image/${blogImage.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/blog-image/${blogImage.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
          !loadingImages && (
            <div className="alert alert-warning">
              <Translate contentKey="diaryFibreApp.blogImage.home.notFound">No Blog Images found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BlogImage;
