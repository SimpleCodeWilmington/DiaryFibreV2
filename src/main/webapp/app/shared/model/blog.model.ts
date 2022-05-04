import { IUser } from 'app/shared/model/user.model';
import { Template } from 'app/shared/model/enumerations/template.model';
import { AccessType } from 'app/shared/model/enumerations/access-type.model';

export interface IBlog {
  id?: number;
  blogName?: string;
  blogOwner?: string;
  template?: Template | null;
  accessStatus?: AccessType;
  user?: IUser | null;
}

export const defaultValue: Readonly<IBlog> = {};
