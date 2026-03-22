export type LoginResponse = {
  user: {
    id: string;
    username: string;
    picture: string | null;
    description?: string | null;
    createdAt: string;
  };
  token: string;
};
