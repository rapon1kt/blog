import { loginAccount } from "@/actions/auth/login-account";
import type { LoginResponse } from "@/types/auth";
import { jwtDecode } from "jwt-decode";
import type { NextAuthOptions, User } from "next-auth";
import Credentials from "next-auth/providers/credentials";

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

        const data = await loginAccount(username, password);
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
