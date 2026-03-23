export type JwtPayload = {
  sub: string;
  role: string;
  username: string;
};

export type PublicAccount = {
  id: string;
  username: string;
  picture: string | null;
  description: string | null;
  createdAt: Date;
};

export type PostVisibility = "PUBLIC" | "PRIVATE" | "FRIENDS" | "FOLLOWERS";

export type PublicPost = {
  id: string;
  title: string;
  content: string;
  postVisibility: PostVisibility;
  likeCount: number;
  authorId: string;
  pinned: boolean;
  createdAt: Date;
  modifiedAt: Date;
};

export type Post = PublicPost;

export type Comment = {
  id: string;
  content: string;
  authorId: string;
  postId: string;
  commentId: string | null;
  answer: boolean;
  likeCount: number;
  createdAt: Date;
  modifiedAt: Date;
};
