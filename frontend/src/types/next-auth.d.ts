import "next-auth";
import "next-auth/jwt";

declare module "next-auth" {
  interface User {
    id: string;
    token: string;
    user: {
      id: string;
      username: string;
      picture: string;
      description?: string | null;
      createdAt: string;
    };
  }
  interface Session {
    accessToken?: string;
    error?: "TokenExpired";
    user?: {
      id: string;
      username: string;
      picture: string;
      description?: string | null;
      createdAt: string;
    };
  }
}

declare module "next-auth/jwt" {
  interface JWT {
    user?: {
      id: string;
      username: string;
      picture: string;
      description?: string | null;
      createdAt: string;
    };
    exp?: number;
    accessToken?: string;
    error?: "TokenExpired";
  }
}
