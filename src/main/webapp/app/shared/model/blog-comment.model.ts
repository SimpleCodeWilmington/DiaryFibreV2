import dayjs from 'dayjs';
import { IBlogPost } from 'app/shared/model/blog-post.model';
import { IUser } from 'app/shared/model/user.model';

export interface IBlogComment {
  id?: number;
  comment?: string;
  dateTime?: string | null;
  blog?: IBlogPost | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IBlogComment> = {};
