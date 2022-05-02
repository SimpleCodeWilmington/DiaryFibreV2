import './tags.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert, Button } from 'reactstrap';
import { useAppSelector } from 'app/config/store';

export const Tags = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div class="wrapper">
      <input type="text" id="tags" autocomplete="off">
      </input>
      <div class="tag-container">
      </div>
    </div>
  );
};

export default Tags;
