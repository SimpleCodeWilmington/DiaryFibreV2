import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blog-image.reducer';

export const BlogImageDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const blogImageEntity = useAppSelector(state => state.blogImage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="blogImageDetailsHeading">
          <Translate contentKey="diaryFibreApp.blogImage.detail.title">BlogImage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{blogImageEntity.id}</dd>
          <dt>
            <span id="blogImage">
              <Translate contentKey="diaryFibreApp.blogImage.blogImage">Blog Image</Translate>
            </span>
          </dt>
          <dd>
            {blogImageEntity.blogImage ? (
              <div>
                {blogImageEntity.blogImageContentType ? (
                  <a onClick={openFile(blogImageEntity.blogImageContentType, blogImageEntity.blogImage)}>
                    <img
                      src={`data:${blogImageEntity.blogImageContentType};base64,${blogImageEntity.blogImage}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {blogImageEntity.blogImageContentType}, {byteSize(blogImageEntity.blogImage)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="imageNumber">
              <Translate contentKey="diaryFibreApp.blogImage.imageNumber">Image Number</Translate>
            </span>
          </dt>
          <dd>{blogImageEntity.imageNumber}</dd>
          <dt>
            <Translate contentKey="diaryFibreApp.blogImage.blogpost">Blogpost</Translate>
          </dt>
          <dd>{blogImageEntity.blogpost ? blogImageEntity.blogpost.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/blog-image" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blog-image/${blogImageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BlogImageDetail;
