import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';

export const AddComment = () => {
  return (
    <Link to="/create-comment" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
      <FontAwesomeIcon icon="plus" />
      &nbsp;
      <Translate contentKey="diaryFibreApp.blogComment.home.createLabel">Create new Blog Comment</Translate>
    </Link>
  );
};

export default AddComment;
