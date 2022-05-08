import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blog-post.reducer';

export const BlogPostDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const blogPostEntity = useAppSelector(state => state.blogPost.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="blogPostDetailsHeading">
          <Translate contentKey="diaryFibreApp.blogPost.detail.title">BlogPost</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{blogPostEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="diaryFibreApp.blogPost.title">Title</Translate>
            </span>
          </dt>
          <dd>{blogPostEntity.title}</dd>
          <dt>
            <span id="text">
              <Translate contentKey="diaryFibreApp.blogPost.text">Text</Translate>
            </span>
          </dt>
          <dd>{blogPostEntity.text}</dd>
          <dt>
            <span id="dateTime">
              <Translate contentKey="diaryFibreApp.blogPost.dateTime">Date Time</Translate>
            </span>
          </dt>
          <dd>{blogPostEntity.dateTime ? <TextFormat value={blogPostEntity.dateTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="template">
              <Translate contentKey="diaryFibreApp.blogPost.template">Template</Translate>
            </span>
          </dt>
          <dd>{blogPostEntity.template}</dd>
          <dt>
            <Translate contentKey="diaryFibreApp.blogPost.blogtext">Blogtext</Translate>
          </dt>
          <dd>{blogPostEntity.blogtext ? blogPostEntity.blogtext.id : ''}</dd>
          <dt>
            <Translate contentKey="diaryFibreApp.blogPost.blog">Blog</Translate>
          </dt>
          <dd>{blogPostEntity.blog ? blogPostEntity.blog.id : ''}</dd>
          <dt>
            <Translate contentKey="diaryFibreApp.blogPost.tag">Tag</Translate>
          </dt>
          <dd>
            {blogPostEntity.tags
              ? blogPostEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {blogPostEntity.tags && i === blogPostEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/blog-post" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/blog-post/${blogPostEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BlogPostDetail;
