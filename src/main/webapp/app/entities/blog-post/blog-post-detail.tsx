import './blog-post-detail.scss';

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
    <Col md="7" className="column">
      <div className="blog-post-and-comments">

        <div className="date-time">
          <dd>{blogPostEntity.dateTime ? <TextFormat value={blogPostEntity.dateTime} type="date" format={POST_DATE_FORMAT} /> : null}</dd>
        </div>

        <dl className="jh-entity-details">

          <div className="post-title">
            <dd>{blogPostEntity.title}</dd>
          </div>

          <div {...props} className="post-image">
            <img src="content/images/icecream.jpg" className="centered" />
          </div>

          <div className="post-text">
            <dd>{blogPostEntity.text}</dd>
          </div>

          <dd className="tagSpan">
            {blogPostEntity.tags
              ? blogPostEntity.tags.map((val, i) => (
                <span key={val.tagName}>
                  <span className="tag">{val.tagName}</span>
                  {blogPostEntity.tags && i === blogPostEntity.tags.length - 1 ? '' : '   '}
                </span>
              ))
            : null}
          </dd>
        </dl>
        <Comments />
      </div>
  </Col>
  <Col md="5" className="column2">
    <span className="blogName">{blogPostEntity.blog ? blogPostEntity.blog.blogName : ''}</span>
    <div {...props} className="post-image">
      <img src="content/images/Flint.png" className="centered" />
    </div>  
  </Col>
  </Row>
  );
};

export default BlogPostDetail;
