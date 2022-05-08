import React, { useState, useEffect } from 'react';
import { Button, Table } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, TextFormat } from 'react-jhipster';
import { getEntities } from 'app/entities/blog-comment/blog-comment.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';

const dispatch = useAppDispatch();

const loading = useAppSelector(state => state.blogComment.loading);

const handleSyncList = () => {
  dispatch(getEntities({}));
};

export const Refresh = () => {
  return (
    <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
      <FontAwesomeIcon icon="sync" spin={loading} />{' '}
      <Translate contentKey="diaryFibreApp.blogComment.home.refreshListLabel">Refresh List</Translate>
    </Button>
  );
};

export default Refresh;
