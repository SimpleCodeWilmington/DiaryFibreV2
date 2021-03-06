import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BlogPost from './blog-post';
import BlogPostDetail from './blog-post-detail';
import BlogPostUpdate from './blog-post-update';
import BlogPostDeleteDialog from './blog-post-delete-dialog';
import MyBlogsEdit from 'app/modules/myblogs/myblogs-edit';
import MyBlogsDeleteDialog from 'app/modules/myblogs/myblogs-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BlogPostUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BlogPostUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BlogPostDetail} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MyBlogsEdit} />
      <ErrorBoundaryRoute path={match.url} component={BlogPost} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BlogPostDeleteDialog} />
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MyBlogsDeleteDialog} />
  </>
);

export default Routes;
