import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';

//lalala just for commiting purpose, please work


export const AddComment = () => {
  return (
    <Link to="/blog-comment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
      <FontAwesomeIcon icon="plus" />
      &nbsp;
      <Translate contentKey="diaryFibreApp.blogComment.home.createLabel">Create new Blog Comment</Translate>
    </Link>
  );
};

export default AddComment;
