import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: 75, fontFamily: 'Garamond' }}>
      <img src="content/images/DF Home Page.png" />

      {account?.login ? (
        <div>
          <Translate contentKey="home.subtitle" interpolate={{ username: account.firstName }}>
            Welcome, {account.firstName}
          </Translate>
        </div>
      ) : (
        <div>
          <Translate contentKey="home.title" interpolate={{ username: account.firstName }}>
            Welcome, to Diary Fibre
          </Translate>
        </div>
      )}
    </div>
  );
};

export default Home;
