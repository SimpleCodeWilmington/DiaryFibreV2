import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/blog">
        <Translate contentKey="global.menu.entities.blog" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/blog-post">
        <Translate contentKey="global.menu.entities.blogPost" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/blog-text">
        <Translate contentKey="global.menu.entities.blogText" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/blog-image">
        <Translate contentKey="global.menu.entities.blogImage" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tag">
        <Translate contentKey="global.menu.entities.tag" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
