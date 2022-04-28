import { IUser } from 'app/shared/model/user.model';
import { AccessType } from 'app/shared/model/enumerations/access-type.model';

export interface IBlog {
  id?: number;
  blogName?: string;
  blogOwner?: string;
  accessStatus?: AccessType;
  user?: IUser | null;
}

export const defaultValue: Readonly<IBlog> = {};
