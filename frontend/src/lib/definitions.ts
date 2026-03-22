export type JwtPayload = {
  sub: string;
  role: string;
  username: string;
};

export type PublicAccount = {
  id: string;
  username: string;
  picture: string;
  description: string | null;
  createdAt: Date;
};

export type Post = {
  id: string;
  title: string;
  content: string;
  postVisibility: "public" | "private" | "friends" | "followers";
  likeCount: number;
  authorId: string;
  pinned: boolean;
  createdAt: Date;
  modifiedAt: Date;
};

export type Comment = {
  id: string;
  content: string;
  authorId: string;
  postId: string;
  commentId: string;
  answer: boolean;
  likeCount: number;
  createdAt: Date;
  modifiedAt: Date;
};
