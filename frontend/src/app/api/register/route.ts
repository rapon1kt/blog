import { registerAccount } from "@/actions/auth/register-account";
import { NextResponse } from "next/server";

export async function POST(request: Request) {
  let payload: unknown;
  try {
    payload = await request.json();
  } catch {
    return NextResponse.json(
      { message: "Invalid JSON body." },
      { status: 400 },
    );
  }

  if (
    !payload ||
    typeof payload !== "object" ||
    typeof (payload as { username?: unknown }).username !== "string" ||
    typeof (payload as { email?: unknown }).email !== "string" ||
    typeof (payload as { password?: unknown }).password !== "string"
  ) {
    return NextResponse.json(
      { message: "username, email, and password are required." },
      { status: 400 },
    );
  }

  const { username, email, password } = payload as {
    username: string;
    email: string;
    password: string;
  };

  const result = await registerAccount({ username, email, password });
  if (!result.ok) {
    return NextResponse.json(
      { message: result.message },
      { status: result.status },
    );
  }

  return NextResponse.json(result.data, { status: 201 });
}
