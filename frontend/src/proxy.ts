import { getToken } from "next-auth/jwt";
import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
import { isTokenExpired } from "@/utils/auth/is-token-expired";

const AUTH_PAGES = new Set(["/", "/register"]);

export async function proxy(request: NextRequest) {
  const { pathname } = request.nextUrl;
  if (!AUTH_PAGES.has(pathname)) {
    return NextResponse.next();
  }

  const secret = process.env.NEXTAUTH_SECRET ?? process.env.AUTH_SECRET;
  if (!secret) {
    return NextResponse.next();
  }

  const token = await getToken({
    req: request,
    secret,
  });

  const sessionUser = token?.user as { id?: string } | undefined;
  const isValidSession =
    !!token && !isTokenExpired(token.exp) && !!sessionUser?.id;

  if (isValidSession) {
    return NextResponse.redirect(new URL("/home", request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: ["/", "/register"],
};
