import React, { useEffect } from 'react';
import { Translate, TextFormat } from 'react-jhipster';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
// import { getEntities } from './comment-reducer';
import { Col, Row } from 'reactstrap';
import { Button, Table } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';

export const EditComment = () => {
 

  return (
    <Button color="white" size="sm" data-cy="entityEditButton">
    <FontAwesomeIcon icon="pencil-alt" />{' '}
    <span className="d-none d-md-inline">
      <Translate contentKey="entity.action.edit">Edit</Translate>
    </span>
  </Button>
  );
};

export default EditComment;
