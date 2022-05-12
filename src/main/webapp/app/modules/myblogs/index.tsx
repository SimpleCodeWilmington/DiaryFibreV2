import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MyBlogPost from 'app/modules/myblogs';
import BlogPostDetail from 'app/modules/myblogs/blog-post-detail';              //Complete this
import BlogPostUpdate from 'app/modules/myblogs/blog-post-update';              //Complete this
import BlogPostDeleteDialog from 'app/modules/myblogs/myblogs-delete-dialog';   //Complete this
import MyBlogsEdit from 'app/modules/myblogs/myblogs-edit';
import MyBlogsDeleteDialog from 'app/modules/myblogs/myblogs-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BlogPostUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BlogPostUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BlogPostDetail} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MyBlogsEdit} />
      <ErrorBoundaryRoute path={match.url} component={MyBlogPost} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BlogPostDeleteDialog} />
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MyBlogsDeleteDialog} />
  </>
);

export default Routes;
