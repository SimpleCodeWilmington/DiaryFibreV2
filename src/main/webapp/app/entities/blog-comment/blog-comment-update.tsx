import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBlogPost } from 'app/shared/model/blog-post.model';
import { getEntities as getBlogPosts } from 'app/entities/blog-post/blog-post.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IBlogComment } from 'app/shared/model/blog-comment.model';
import { getEntity, updateEntity, createEntity, reset } from './blog-comment.reducer';

export const BlogCommentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

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

  const defaultValues = () =>
    isNew
      ? {
          dateTime: displayDefaultDateTime(),
        }
      : {
          ...blogCommentEntity,
          dateTime: convertDateTimeFromServer(blogCommentEntity.dateTime),
          blog: blogCommentEntity?.blog?.id,
          user: blogCommentEntity?.user?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
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
                label={translate('diaryFibreApp.blogComment.comment')}
                id="blog-comment-comment"
                name="comment"
                data-cy="comment"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('diaryFibreApp.blogComment.dateTime')}
                id="blog-comment-dateTime"
                name="dateTime"
                data-cy="dateTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/commentspage" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BlogCommentUpdate;
