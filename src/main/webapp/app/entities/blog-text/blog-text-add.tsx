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
import { AccessType } from 'app/shared/model/enumerations/access-type.model';
import { getEntity, updateEntity, createEntity, reset } from 'app/entities/blog/blog.reducer';

export const BlogTextAdd = () => {
  const account = useAppSelector(state => state.authentication.account);
    const dispatch = useAppDispatch();

    const users = useAppSelector(state => state.userManagement.users);
    const blogEntity = useAppSelector(state => state.blog.entity);

    const saveEntity = values => {
      const entity = {
        ...blogEntity,
        ...values,
        user: users.find(it => it.id.toString() === values.user.toString()),
      };
      dispatch(createEntity(entity));
    };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
           <ValidatedForm onSubmit={saveEntity}>
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
            </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default BlogTextAdd;

