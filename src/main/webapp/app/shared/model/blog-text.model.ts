export interface IBlogText {
  id?: number;
  blogTextContentType?: string | null;
  blogText?: string | null;
}

export const defaultValue: Readonly<IBlogText> = {};
