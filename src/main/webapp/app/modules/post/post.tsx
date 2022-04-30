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
        <p className="lead">
          <Translate contentKey="post.subtitle">This is your postpage</Translate>
        </p>
        {account?.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="post.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>

              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
              <Translate contentKey="global.messages.info.authenticated.suffix">
                , you can try the default accounts:
                <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
                <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
              </Translate>
            </Alert>

            <Alert color="warning">
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </Alert>
          </div>
        )}
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
