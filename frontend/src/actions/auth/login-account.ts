import type { LoginResponse } from "@/types/auth";
import type { BackendErrorBody } from "@/types/backend";
import { getBackendBaseUrl } from "@/utils/backend/base-url";
import { readBackendErrorMessage } from "@/utils/http/read-backend-error-message";
import { safeJson } from "@/utils/http/safe-json";

function isLoginResponse(data: unknown): data is LoginResponse {
  if (!data || typeof data !== "object") return false;
  const response = data as LoginResponse;

  return (
    typeof response.token === "string" &&
    !!response.user &&
    typeof response.user.id === "string" &&
    typeof response.user.username === "string"
  );
}

export async function loginAccount(username: string, password: string) {
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

  const data = await safeJson<unknown>(response);

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

  return data;
}
