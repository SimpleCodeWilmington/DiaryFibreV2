import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IBlog } from 'app/shared/model/blog.model';
import { Template } from 'app/shared/model/enumerations/template.model';
import { AccessType } from 'app/shared/model/enumerations/access-type.model';
import { getEntity, updateEntity, createEntity, reset } from './blog.reducer';

export const BlogUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const blogEntity = useAppSelector(state => state.blog.entity);
  const loading = useAppSelector(state => state.blog.loading);
  const updating = useAppSelector(state => state.blog.updating);
  const updateSuccess = useAppSelector(state => state.blog.updateSuccess);
  const templateValues = Object.keys(Template);
  const accessTypeValues = Object.keys(AccessType);
  const handleClose = () => {
    props.history.push('/blog' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...blogEntity,
      ...values,
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
      ? {}
      : {
          template: 'THEDAVID',
          accessStatus: 'Public',
          ...blogEntity,
          user: blogEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diaryFibreApp.blog.home.createOrEditLabel" data-cy="BlogCreateUpdateHeading">
            <Translate contentKey="diaryFibreApp.blog.home.createOrEditLabel">Create or edit a Blog</Translate>
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
                  id="blog-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diaryFibreApp.blog.blogName')}
                id="blog-blogName"
                name="blogName"
                data-cy="blogName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('diaryFibreApp.blog.blogOwner')}
                id="blog-blogOwner"
                name="blogOwner"
                data-cy="blogOwner"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('diaryFibreApp.blog.template')}
                id="blog-template"
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
              <ValidatedField
                label={translate('diaryFibreApp.blog.accessStatus')}
                id="blog-accessStatus"
                name="accessStatus"
                data-cy="accessStatus"
                type="select"
              >
                {accessTypeValues.map(accessType => (
                  <option value={accessType} key={accessType}>
                    {translate('diaryFibreApp.AccessType.' + accessType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="blog-user" name="user" data-cy="user" label={translate('diaryFibreApp.blog.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/blog" replace color="info">
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

export default BlogUpdate;
