import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getBlogPosts } from 'app/entities/blog-post/blog-post.reducer';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from 'app/entities/blog-comment/blog-comment.reducer';

export const BlogCommentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);
  const today = new Date();
  const time = today.getHours() + ':' + today.getMinutes() + ':' + today.getSeconds();
  const date = `${today.getMonth() + 1}/${today.getDate()}/${today.getFullYear()}`;
  const blogPosts = useAppSelector(state => state.blogPost.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const blogCommentEntity = useAppSelector(state => state.blogComment.entity);
  const loading = useAppSelector(state => state.blogComment.loading);
  const updating = useAppSelector(state => state.blogComment.updating);
  const updateSuccess = useAppSelector(state => state.blogComment.updateSuccess);
  const handleClose = () => {
    props.history.push('/blog-comment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBlogPosts({}));
    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateTime = convertDateTimeToServer(values.dateTime);

    const entity = {
      ...blogCommentEntity,
      ...values,
      blog: blogPosts.find(it => it.id.toString() === values.blog.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diaryFibreApp.blogComment.home.createOrEditLabel" data-cy="BlogCommentCreateUpdateHeading">
            <Translate contentKey="diaryFibreApp.blogComment.home.createOrEditLabel">Create or edit a BlogComment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="blog-comment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="blog-comment-user"
                name="user"
                data-cy="user"
                label={translate('diaryFibreApp.blogComment.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="blog-comment-blog"
                name="blog"
                data-cy="blog"
                label={translate('diaryFibreApp.blogComment.blog')}
                type="select"
              >
                <option value="" key="0" />
                {blogPosts
                  ? blogPosts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('diaryFibreApp.blogComment.comment')}
                id="blog-comment-comment"
                name="comment"
                data-cy="comment"
                type="textarea"
                placeholder="Comment cannot exceed 250 characters!"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  // maxLength: {
                  //   value: 300,
                  //   message: translate('entity.validation.maxlength', { max: 300 }),
                  // },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/post" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <Translate contentKey="entity.action.submit">Submit</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BlogCommentUpdate;