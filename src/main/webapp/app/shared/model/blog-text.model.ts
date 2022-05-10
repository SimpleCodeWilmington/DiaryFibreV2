export interface IBlogText {
  id?: number;
  text?: string;
  blogTextContentType?: string | null;
  blogText?: string | null;
}

export const defaultValue: Readonly<IBlogText> = {};
