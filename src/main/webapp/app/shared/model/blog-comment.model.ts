import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IBlogPost } from 'app/shared/model/blog-post.model';

export interface IBlogComment {
  id?: number;
  comment?: string;
  dateTime?: string | null;
  user?: IUser | null;
  blogPost?: IBlogPost | null;
}

export const defaultValue: Readonly<IBlogComment> = {};
