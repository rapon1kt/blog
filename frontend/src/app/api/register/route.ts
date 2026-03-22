import { NextResponse } from "next/server";
import {
  getBackendBaseUrl,
  readBackendErrorMessage,
  type BackendErrorBody,
} from "@/lib/backend";

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

  let base: string;
  try {
    base = getBackendBaseUrl();
  } catch {
    return NextResponse.json(
      { message: "Server configuration error." },
      { status: 500 },
    );
  }

  const response = await fetch(`${base}/req/signup`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, email, password }),
  });

  let data: BackendErrorBody | Record<string, unknown> | null = null;
  try {
    data = (await response.json()) as BackendErrorBody;
  } catch {
    data = null;
  }

  if (!response.ok) {
    const message = readBackendErrorMessage(
      data,
      response.status === 409
        ? "That username or email is already taken."
        : "Registration failed.",
    );
    return NextResponse.json({ message }, { status: response.status });
  }

  return NextResponse.json(data ?? {}, { status: 201 });
}
