import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBlogPost } from 'app/shared/model/blog-post.model';
import { getEntities as getBlogPosts } from 'app/entities/blog-post/blog-post.reducer';
import { IBlogText } from 'app/shared/model/blog-text.model';
import { getEntity, updateEntity, createEntity, reset } from './blog-text.reducer';

export const BlogTextUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const blogPosts = useAppSelector(state => state.blogPost.entities);
  const blogTextEntity = useAppSelector(state => state.blogText.entity);
  const loading = useAppSelector(state => state.blogText.loading);
  const updating = useAppSelector(state => state.blogText.updating);
  const updateSuccess = useAppSelector(state => state.blogText.updateSuccess);
  const handleClose = () => {
    props.history.push('/blog-text');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBlogPosts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...blogTextEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...blogTextEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diaryFibreApp.blogText.home.createOrEditLabel" data-cy="BlogTextCreateUpdateHeading">
            <Translate contentKey="diaryFibreApp.blogText.home.createOrEditLabel">Create or edit a BlogText</Translate>
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
                  id="blog-text-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diaryFibreApp.blogText.text')}
                id="blog-text-text"
                name="text"
                data-cy="text"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedBlobField
                label={translate('diaryFibreApp.blogText.blogText')}
                id="blog-text-blogText"
                name="blogText"
                data-cy="blogText"
                openActionLabel={translate('entity.action.open')}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/blog-text" replace color="info">
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

export default BlogTextUpdate;
