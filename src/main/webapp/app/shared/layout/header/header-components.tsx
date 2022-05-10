import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const BrandIcon = props => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <div {...props} className="brand-icon">
      <img src="content/images/Diary Fibre (500 × 200 px) (2).png" alt="Logo" />
    </div>
  </NavbarBrand>
);

export const Brand = () => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <BrandIcon />
    <span className="brand-title">
      <Translate contentKey="global.title">DiaryFibre</Translate>
    </span>
  </NavbarBrand>
);

export const Home = () => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>
        <Translate contentKey="global.menu.home">Home</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const Post = () => (
  <NavItem>
    <NavLink tag={Link} to="/post" className="d-flex align-items-center">
      <span>POST</span>
    </NavLink>
  </NavItem>

);

export const MyBlogs = () => (
  <NavItem>
    <NavLink tag={Link} to="/myblogs" className="d-flex align-items-center">
      <span>MyBlogs</span>
    </NavLink>
  </NavItem>
);

export const Comments = () => (
  <NavItem>
    <NavLink tag={Link} to="/commentspage" className="d-flex align-items-center">
      <span>Comments</span>
    </NavLink>
  </NavItem>

);
