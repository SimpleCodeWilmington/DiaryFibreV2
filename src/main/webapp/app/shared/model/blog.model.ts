import { AccessType } from 'app/shared/model/enumerations/access-type.model';

export interface IBlog {
  id?: number;
  blogName?: string;
  blogOwner?: string;
  accessStatus?: AccessType;
}

export const defaultValue: Readonly<IBlog> = {};
