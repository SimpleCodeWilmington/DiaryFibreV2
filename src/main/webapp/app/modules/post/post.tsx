import './post.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert, Button } from 'reactstrap';
import AddComment from 'app/modules/comments/add-comment';
import { useAppSelector } from 'app/config/store';

import { BlogTextAdd } from 'app/entities/blog-text/blog-text-add';

export const Post = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="7" className="pad">
        <div className="blog-text-add">
          <BlogTextAdd />
        </div>
        <div className="add-comment">
          <AddComment />
        </div>
      </Col>
      <Col md="5">
        <div className="flex-parent jc-center" style={{margin: '15px 0'}}>
            <Link to="/post">
              <Button className="simple-post" type="button">
                <span className="black">Simple</span>
              </Button>
            </Link>
            <Link to="/post-advanced">
              <Button className="advanced-post" type="button">
                <span className="white">Advanced</span>
              </Button>
            </Link>
        </div>
        <div className="flex-parent jc-center" style={{margin: '15px 0'}}>
            <h5>Upload Image</h5>
        </div>
        <div className="flex-parent jc-center" style={{margin: '15px 0'}}>
            <div className="upload-image">
              <img src="content/images/IMAGE.png" />
            </div>
        </div>
        <div className="flex-parent jc-center" style={{margin: '15px 0'}}>
              <Button className="save-as-draft" type="button">
                <span>Save As Draft</span>
              </Button>
              <Button className="post-to-blog" type="button">
                <span>Post</span>
              </Button>
        </div>
      </Col>
    </Row>
  );
};

export default Post;
