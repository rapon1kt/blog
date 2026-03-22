import type { BackendErrorBody } from "@/types/backend";
import { getBackendBaseUrl } from "@/utils/backend/base-url";
import { readBackendErrorMessage } from "@/utils/http/read-backend-error-message";
import { safeJson } from "@/utils/http/safe-json";

type RegisterAccountInput = {
  username: string;
  email: string;
  password: string;
};

type RegisterAccountResult =
  | { ok: true; data: Record<string, unknown> }
  | { ok: false; message: string; status: number };

export async function registerAccount(
  input: RegisterAccountInput,
): Promise<RegisterAccountResult> {
  let base: string;
  try {
    base = getBackendBaseUrl();
  } catch {
    return {
      ok: false,
      message: "Server configuration error.",
      status: 500,
    };
  }

  const response = await fetch(`${base}/req/signup`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(input),
  });

  const data = await safeJson<BackendErrorBody | Record<string, unknown>>(response);

  if (!response.ok) {
    return {
      ok: false,
      message: readBackendErrorMessage(
        data as BackendErrorBody | null,
        response.status === 409
          ? "That username or email is already taken."
          : "Registration failed.",
      ),
      status: response.status,
    };
  }

  return {
    ok: true,
    data: data ?? {},
  };
}
