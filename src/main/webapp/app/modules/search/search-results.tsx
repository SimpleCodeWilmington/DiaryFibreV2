import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SearchResults = () => {
  const dispatch = useAppDispatch();
  
  return (
    <div>
        <h2>SEARCH RESULTS GO HERE</h2>
    </div>
  );
};

export default SearchResults;