import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => {
  return (
    <div className="footer page-content">
      <Row>
        <Col md="12">
        <div>
          <p>
          <img src="content/images/footer.png" alt="Logo" />
            <Translate contentKey="footer">Your footer</Translate>
          </p>
          </div>
        </Col>
      </Row>
    </div>
  );
};

export default Footer;
