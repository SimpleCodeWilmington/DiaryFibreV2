import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SearchBar = () => {
  const dispatch = useAppDispatch();
  const [searchTerm, setSearchTerm] = useState("");

  return (
    <div className="SearchBar">
        <input
            type='text'
            placeholder='Search'
            onChange={(event) => {
                setSearchTerm(event.target.value);
            }}
            >
        </input>
    </div>
  );
};

export default SearchBar;

// when you hit enter - redirects to search results page
// search results page returns blog posts with a title or tag that matches the search term
