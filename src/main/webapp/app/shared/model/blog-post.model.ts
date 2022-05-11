import dayjs from 'dayjs';
import { IBlogImage } from 'app/shared/model/blog-image.model';
import { IBlogComment } from 'app/shared/model/blog-comment.model';
import { IBlog } from 'app/shared/model/blog.model';
import { ITag } from 'app/shared/model/tag.model';
import { Template } from 'app/shared/model/enumerations/template.model';

export interface IBlogPost {
  id?: number;
  title?: string;
  text?: string;
  dateTime?: string | null;
  template?: Template | null;
  blogImages?: IBlogImage[] | null;
  blogComments?: IBlogComment[] | null;
  blog?: IBlog | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<IBlogPost> = {};
