import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';

import { POST_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blog-post.reducer';
import { Comments } from 'app/modules/comments/comments'

export const BlogPostDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const blogPostEntity = useAppSelector(state => state.blogPost.entity);
  return (
    <Row>
      
      <Col md="8">

        <div className="date-time">
          <dd>{blogPostEntity.dateTime ? <TextFormat value={blogPostEntity.dateTime} type="date" format={POST_DATE_FORMAT} /> : null}</dd>
        </div>

        <dl className="jh-entity-details">

          <div className="post-title">
            <dd>{blogPostEntity.title}</dd>
          </div>

          <div {...props} className="brand-icon">
            <img src="content/images/ferris-wheel.jpg" />
          </div>

          <div className="post-text">
            <dd>{blogPostEntity.text}</dd>
          </div>


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
        <Comments />
      </Col>
    </Row>
  );
};

export default BlogPostDetail;
