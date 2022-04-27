import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BlogText from './blog-text';
import BlogTextDetail from './blog-text-detail';
import BlogTextUpdate from './blog-text-update';
import BlogTextDeleteDialog from './blog-text-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BlogTextUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BlogTextUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BlogTextDetail} />
      <ErrorBoundaryRoute path={match.url} component={BlogText} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BlogTextDeleteDialog} />
  </>
);

export default Routes;
