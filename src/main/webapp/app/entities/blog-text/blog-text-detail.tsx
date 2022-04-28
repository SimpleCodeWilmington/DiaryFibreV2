import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blog-text.reducer';

export const BlogTextDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const blogTextEntity = useAppSelector(state => state.blogText.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="blogTextDetailsHeading">
          <Translate contentKey="diaryFibreApp.blogText.detail.title">BlogText</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{blogTextEntity.id}</dd>
          <dt>
            <span id="blogText">
              <Translate contentKey="diaryFibreApp.blogText.blogText">Blog Text</Translate>
            </span>
          </dt>
          <dd>
            {blogTextEntity.blogText ? (
              <div>
                {blogTextEntity.blogTextContentType ? (
                  <a onClick={openFile(blogTextEntity.blogTextContentType, blogTextEntity.blogText)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {blogTextEntity.blogTextContentType}, {byteSize(blogTextEntity.blogText)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/blog-text" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blog-text/${blogTextEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BlogTextDetail;
