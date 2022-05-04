import dayjs from 'dayjs';
import { IBlogText } from 'app/shared/model/blog-text.model';
import { IBlogImage } from 'app/shared/model/blog-image.model';
import { IBlog } from 'app/shared/model/blog.model';
import { ITag } from 'app/shared/model/tag.model';
import { Template } from 'app/shared/model/enumerations/template.model';

export interface IBlogPost {
  id?: number;
  title?: string;
  dateTime?: string | null;
  template?: Template | null;
  blogtext?: IBlogText | null;
  blogImages?: IBlogImage[] | null;
  blog?: IBlog | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<IBlogPost> = {};
