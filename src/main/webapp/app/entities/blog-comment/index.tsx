import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BlogComment from './blog-comment';
import BlogCommentDetail from './blog-comment-detail';
import BlogCommentUpdate from './blog-comment-update';
import BlogCommentDeleteDialog from './blog-comment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BlogCommentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BlogCommentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BlogCommentDetail} />
      <ErrorBoundaryRoute path={match.url} component={BlogComment} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BlogCommentDeleteDialog} />
  </>
);

export default Routes;
