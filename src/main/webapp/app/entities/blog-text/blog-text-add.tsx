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
import { getEntities as getBlogs } from 'app/entities/blog/blog.reducer';
import { AccessType } from 'app/shared/model/enumerations/access-type.model';
import { getEntity, updateEntity, createEntity, reset } from 'app/entities/blog/blog.reducer';

export const BlogTextAdd = () => {
  const account = useAppSelector(state => state.authentication.account);
    const dispatch = useAppDispatch();

    const users = useAppSelector(state => state.userManagement.users);
    const blogs = useAppSelector(state => state.blog.entities);
    const blogEntity = useAppSelector(state => state.blog.entity);

      useEffect(() => {
        dispatch(getBlogs({}));
      }, []);

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
              <ValidatedField id="blog-post-blog" name="blog" data-cy="blog" label={translate('diaryFibreApp.blogPost.blog')} type="select">
                <option value="" key="0" />
                {blogs
                  ? blogs.map(otherEntity => (
                      <option value={otherEntity.blogName} key={otherEntity.id}>
                        {otherEntity.blogName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
            </ValidatedForm>
            <textarea id="blogtitle" name="blogtitle" rows={1} cols={50}>
            </textarea>
            <textarea id="blogtext" name="blogtext" rows={10} cols={50}>
            </textarea>
        </Col>
      </Row>
    </div>
  );
};

export default BlogTextAdd;

