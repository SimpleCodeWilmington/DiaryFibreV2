import './blog-post-detail.scss';

import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';

import { APP_DATE_FORMAT, POST_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './blog-post.reducer';
import { Comments } from 'app/modules/comments/comments'

export const BlogPostDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const blogPostEntity = useAppSelector(state => state.blogPost.entity);



  // logcomments
  const blogCommentList = useAppSelector(state => state.blogComment.entities);
  const loading = useAppSelector(state => state.blogComment.loading);

  // BlogComments


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
          <div className="table-responsive-comment">
          {blogCommentList && blogCommentList.length > 0 ? (
                       <Table responsive>
                         <tbody>
                        {(blogCommentList).map((comments, i) => (
                          <tr key={`entity-${i}`} data-cy="entityTable">

                            {comments.blogPost ?  (


                            <div>

                            {comments.blogPost.id === blogPostEntity.id ? (

                            <div>


                            <li className="list-group-item pl-0" key={comments._id} >
                              <p className="text-muted mb-1" >
                                Posted by {comments.user ? comments.user.firstName : ''} on{' '}
                                {comments.dateTime ? <TextFormat type="date" value={comments.dateTime} format={APP_DATE_FORMAT} /> : null}
                              </p>
                              <p className="mb-1">{comments.comment}</p>

                            <div className="btn-group flex-btn-group-container">
                              </div>

                              <div className="btn-group flex-btn-group-container">
                                </div>

                              </li>

                              </div>

                              ): null}

                              </div>

                            ) : null}

                          </tr>
                        ))}
                        </tbody>
                  </Table>
                    ) : (
                      !loading && (
                        <div className="alert alert-warning">
                          <Translate contentKey="diaryFibreApp.blogComment.home.notFound">No Blog Comments found</Translate>
                        </div>
                      )
                    )}
              </div>
      </div>
  </Col>
  <Col md="5" className="column2">
    <hr className="line"></hr>
    <span className="blogName">{blogPostEntity.blog ? blogPostEntity.blog.blogName : ''}</span>
    <hr className="line"></hr>
    <div {...props} className="post-image-ad">
      <img src="content/images/Flint.png" className="centered-ad" />
    </div>
  </Col>
  </Row>
  );
};

export default BlogPostDetail;
