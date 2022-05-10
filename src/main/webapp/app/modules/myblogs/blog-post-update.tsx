import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBlog } from 'app/shared/model/blog.model';
import { getEntities as getBlogs } from 'app/entities/blog/blog.reducer';
import { ITag } from 'app/shared/model/tag.model';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { IBlogPost } from 'app/shared/model/blog-post.model';
import { Template } from 'app/shared/model/enumerations/template.model';
import { getEntity, updateEntity, createEntity, reset } from 'app/modules/myblogs/myblogs.reducer';

export const BlogPostUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const blogs = useAppSelector(state => state.blog.entities);
  const tags = useAppSelector(state => state.tag.entities);
  const blogPostEntity = useAppSelector(state => state.blogPost.entity);
  const loading = useAppSelector(state => state.blogPost.loading);
  const updating = useAppSelector(state => state.blogPost.updating);
  const updateSuccess = useAppSelector(state => state.blogPost.updateSuccess);
  const templateValues = Object.keys(Template);
  const handleClose = () => {
    props.history.push('/blog-post' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBlogs({}));
    dispatch(getTags({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateTime = convertDateTimeToServer(values.dateTime);

    const entity = {
      ...blogPostEntity,
      ...values,
      tags: mapIdList(values.tags),
      blog: blogs.find(it => it.id.toString() === values.blog.toString()),
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
          template: 'THEDAVID',
          ...blogPostEntity,
          dateTime: convertDateTimeFromServer(blogPostEntity.dateTime),
          blog: blogPostEntity?.blog?.id,
          tags: blogPostEntity?.tags?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diaryFibreApp.blogPost.home.createOrEditLabel" data-cy="BlogPostCreateUpdateHeading">
            <Translate contentKey="diaryFibreApp.blogPost.home.createOrEditLabel">Create or edit a BlogPost</Translate>
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
                  id="blog-post-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diaryFibreApp.blogPost.title')}
                id="blog-post-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('diaryFibreApp.blogPost.text')}
                id="blog-post-text"
                name="text"
                data-cy="text"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('diaryFibreApp.blogPost.dateTime')}
                id="blog-post-dateTime"
                name="dateTime"
                data-cy="dateTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('diaryFibreApp.blogPost.template')}
                id="blog-post-template"
                name="template"
                data-cy="template"
                type="select"
              >
                {templateValues.map(template => (
                  <option value={template} key={template}>
                    {translate('diaryFibreApp.Template.' + template)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="blog-post-blog" name="blog" data-cy="blog" label={translate('diaryFibreApp.blogPost.blog')} type="select">
                <option value="" key="0" />
                {blogs
                  ? blogs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('diaryFibreApp.blogPost.tag')}
                id="blog-post-tag"
                data-cy="tag"
                type="select"
                multiple
                name="tags"
              >
                <option value="" key="0" />
                {tags
                  ? tags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/blog-post" replace color="info">
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

export default BlogPostUpdate;
