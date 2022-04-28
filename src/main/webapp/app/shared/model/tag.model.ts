import { IBlogPost } from 'app/shared/model/blog-post.model';

export interface ITag {
  id?: number;
  tagName?: string;
  blogposts?: IBlogPost[] | null;
}

export const defaultValue: Readonly<ITag> = {};
