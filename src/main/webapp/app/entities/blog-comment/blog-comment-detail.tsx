import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blog-comment.reducer';

export const BlogCommentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const blogCommentEntity = useAppSelector(state => state.blogComment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="blogCommentDetailsHeading">
          <Translate contentKey="diaryFibreApp.blogComment.detail.title">BlogComment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{blogCommentEntity.id}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="diaryFibreApp.blogComment.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{blogCommentEntity.comment}</dd>
          <dt>
            <span id="dateTime">
              <Translate contentKey="diaryFibreApp.blogComment.dateTime">Date Time</Translate>
            </span>
          </dt>
          <dd>
            {blogCommentEntity.dateTime ? <TextFormat value={blogCommentEntity.dateTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="diaryFibreApp.blogComment.user">User</Translate>
          </dt>
          <dd>{blogCommentEntity.user ? blogCommentEntity.user.id : ''}</dd>
          <dt>
            <Translate contentKey="diaryFibreApp.blogComment.blogPost">Blog Post</Translate>
          </dt>
          <dd>{blogCommentEntity.blogPost ? blogCommentEntity.blogPost.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/blog-comment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blog-comment/${blogCommentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BlogCommentDetail;
