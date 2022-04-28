import blog from 'app/entities/blog/blog.reducer';
import blogPost from 'app/entities/blog-post/blog-post.reducer';
import blogText from 'app/entities/blog-text/blog-text.reducer';
import blogImage from 'app/entities/blog-image/blog-image.reducer';
import tag from 'app/entities/tag/tag.reducer';
import blogComment from 'app/entities/blog-comment/blog-comment.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  blog,
  blogPost,
  blogText,
  blogImage,
  tag,
  blogComment,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
