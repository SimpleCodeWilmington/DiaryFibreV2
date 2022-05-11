import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { SearchBar } from './search-bar';

export const SearchResults = (props) => {
    const dispatch = useAppDispatch();
    const searchState = props.match.params;
  
    // eslint-disable-next-line no-console
    console.log(searchState)
    
    return (
      <div>
          <h2>SEARCH RESULTS GO HERE</h2>
          <h3>{props.searchTerm}</h3>
      </div>
    );
  };
  
  export default SearchResults;

