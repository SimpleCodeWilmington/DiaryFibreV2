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
            <Translate contentKey="diaryFibreApp.blogPost.blog">Blog</Translate>
          </dt>
          <dd>{blogPostEntity.blog ? blogPostEntity.blog.id : ''}</dd>
          <dt>
            <Translate contentKey="diaryFibreApp.blogPost.tag">Tag</Translate>
          </dt>
          <dd>
            {blogPostEntity.tags
              ? blogPostEntity.tags.map((val, i) => (
                  <span key={val.tagName}>
                    <a>{val.tagName}</a>
                    {blogPostEntity.tags && i === blogPostEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
      </Col>
    </Row>
  );
};

export default BlogPostDetail;
