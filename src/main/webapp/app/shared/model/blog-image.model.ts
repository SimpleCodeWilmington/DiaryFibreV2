import { IBlogPost } from 'app/shared/model/blog-post.model';

export interface IBlogImage {
  id?: number;
  blogImageContentType?: string;
  blogImage?: string;
  imageNumber?: number;
  blogpost?: IBlogPost | null;
}

export const defaultValue: Readonly<IBlogImage> = {};
