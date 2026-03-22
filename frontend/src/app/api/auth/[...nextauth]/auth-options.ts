import {
  getBackendBaseUrl,
  readBackendErrorMessage,
  type BackendErrorBody,
} from "@/lib/backend";
import { jwtDecode } from "jwt-decode";
import type { NextAuthOptions, User } from "next-auth";
import Credentials from "next-auth/providers/credentials";

type LoginResponse = {
  user: {
    id: string;
    username: string;
    picture: string;
    description?: string | null;
    createdAt: string;
  };
  token: string;
};

function isLoginResponse(data: unknown): data is LoginResponse {
  if (!data || typeof data !== "object") return false;
  const d = data as LoginResponse;
  return (
    typeof d.token === "string" &&
    !!d.user &&
    typeof d.user.id === "string" &&
    typeof d.user.username === "string"
  );
}

async function safeJson(res: Response): Promise<unknown | null> {
  const text = await res.text();
  if (!text) return null;
  try {
    return JSON.parse(text) as unknown;
  } catch {
    return null;
  }
}

export const authOptions: NextAuthOptions = {
  session: { strategy: "jwt" },
  pages: {
    signIn: "/",
  },
  providers: [
    Credentials({
      name: "Credentials",
      credentials: {
        username: { label: "Username", type: "text" },
        password: { label: "Password", type: "password" },
      },
      async authorize(credentials) {
        const username = credentials?.username?.trim();
        const password = credentials?.password;
        if (!username || !password) {
          throw new Error("Username and password are required.");
        }

        let base: string;
        try {
          base = getBackendBaseUrl();
        } catch {
          throw new Error("Server configuration error.");
        }

        const response = await fetch(`${base}/req/login`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ username, password }),
        });

        const data = await safeJson(response);

        if (!response.ok) {
          const message = readBackendErrorMessage(
            data as BackendErrorBody | null,
            response.status === 401
              ? "Invalid username or password."
              : "Sign in failed.",
          );
          throw new Error(message);
        }

        if (!isLoginResponse(data)) {
          throw new Error("Unexpected response from server.");
        }

        const account = data.user;
        const token = data.token;

        return {
          id: account.id,
          name: account.username,
          image: account.picture,
          token,
          user: account,
        } as User;
      },
    }),
  ],
  callbacks: {
    async jwt({ token, user }) {
      if (user) {
        const u = user as User & { token?: string; user?: LoginResponse["user"] };
        token.user = u.user;
        token.accessToken = u.token;
        if (u.token) {
          try {
            const decoded = jwtDecode<{ exp?: number }>(u.token);
            if (typeof decoded.exp === "number") {
              token.exp = decoded.exp;
            }
          } catch {
            /* keep NextAuth-managed expiry if decode fails */
          }
        }
      }

      if (
        typeof token.exp === "number" &&
        Date.now() >= token.exp * 1000
      ) {
        return { ...token, error: "TokenExpired" as const };
      }

      return token;
    },
    async session({ session, token }) {
      if (token.user) {
        session.user = token.user;
      }
      session.accessToken = token.accessToken;
      session.error = token.error;
      return session;
    },
  },
};
