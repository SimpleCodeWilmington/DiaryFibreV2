import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Blog from './blog';
import BlogPost from './blog-post';
import BlogText from './blog-text';
import BlogImage from './blog-image';
import Tag from './tag';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}blog`} component={Blog} />
        <ErrorBoundaryRoute path={`${match.url}blog-post`} component={BlogPost} />
        <ErrorBoundaryRoute path={`${match.url}blog-text`} component={BlogText} />
        <ErrorBoundaryRoute path={`${match.url}blog-image`} component={BlogImage} />
        <ErrorBoundaryRoute path={`${match.url}tag`} component={Tag} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
