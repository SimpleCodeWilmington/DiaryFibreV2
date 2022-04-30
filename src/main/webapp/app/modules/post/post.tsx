import './post.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert, Button } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

import { BlogTextAdd } from 'app/entities/blog-text/blog-text-add';

export const Post = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="7">
        <h2>This page is for making blog posts!</h2>
        <Link to="/post">
            <Button type="button">Simple</Button>
        </Link>
        <Link to="/post-advanced">
            <Button type="button">Advanced</Button>
        </Link>
        <h3>Template</h3>
        <h3>Title</h3>
        <h3>Text</h3>
        <h3>Import Images</h3>
        <h3>Tags</h3>
      </Col>
      <Col md="5" className="pad">
        <div className="blog-text-add">
          <BlogTextAdd />
        </div>
      </Col>
    </Row>
  );
};

export default Post;
