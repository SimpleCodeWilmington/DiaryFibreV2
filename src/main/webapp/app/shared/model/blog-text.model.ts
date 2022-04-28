import { IBlogPost } from 'app/shared/model/blog-post.model';

export interface IBlogText {
  id?: number;
  blogTextContentType?: string | null;
  blogText?: string | null;
  blogpost?: IBlogPost | null;
}

export const defaultValue: Readonly<IBlogText> = {};
