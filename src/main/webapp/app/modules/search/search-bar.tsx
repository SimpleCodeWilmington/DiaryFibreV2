import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps, withRouter, useHistory } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SearchBar = () => {
    const dispatch = useAppDispatch();
    const [input, setInput] = useState('');
    const [searchTerm, setSearchTerm] = useState("");
    const history = useHistory();
  
    const onChange = (e) => {
      const { value } = e.target;
      setInput(value);
    };
  
    const onKeyDown = (e) => {
      const { key } = e;
      const trimmedInput = input.trim();
  
      if (key === 'Enter' && trimmedInput.length) {
          e.preventDefault();
          setSearchTerm(trimmedInput);
          // redirect to search-results w/ search term
          history.push({
              pathname: "/search-results",
              state: searchTerm,
          })
      }
    };
  
    return (
      <div className="SearchBar">
          <input
              type='text'
              placeholder='Search'
              value={input}
              onKeyDown={onKeyDown}
              onChange={onChange}
              >
          </input>
      </div>
    );
  };
  
  export default SearchBar;


  
