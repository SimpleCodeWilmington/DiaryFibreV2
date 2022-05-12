import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BlogImage from './blog-image';
import BlogImageDetail from './blog-image-detail';
import BlogImageUpdate from './blog-image-update';
import BlogImageDeleteDialog from './blog-image-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BlogImageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BlogImageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BlogImageDetail} />
      <ErrorBoundaryRoute path={match.url} component={BlogImage} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BlogImageDeleteDialog} />
  </>
);

export default Routes;
