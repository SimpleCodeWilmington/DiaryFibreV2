export interface IBlogText {
  id?: number;
  blogPostID?: number;
  blogTextContentType?: string | null;
  blogText?: string | null;
}

export const defaultValue: Readonly<IBlogText> = {};
