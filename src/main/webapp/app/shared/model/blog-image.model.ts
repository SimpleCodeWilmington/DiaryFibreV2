export interface IBlogImage {
  id?: number;
  blogPostID?: number;
  blogImageContentType?: string;
  blogImage?: string;
  imageNumber?: number;
}

export const defaultValue: Readonly<IBlogImage> = {};
