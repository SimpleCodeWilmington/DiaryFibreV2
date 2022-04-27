import dayjs from 'dayjs';
import { Template } from 'app/shared/model/enumerations/template.model';

export interface IBlogPost {
  id?: number;
  blogID?: number;
  dateTime?: string | null;
  template?: Template;
}

export const defaultValue: Readonly<IBlogPost> = {};
