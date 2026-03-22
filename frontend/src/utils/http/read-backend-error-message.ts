import type { BackendErrorBody } from "@/types/backend";

export function readBackendErrorMessage(
  body: BackendErrorBody | null,
  fallback: string,
): string {
  if (!body) return fallback;
  if (typeof body.message === "string" && body.message.trim()) {
    return body.message.trim();
  }
  if (typeof body.error === "string" && body.error.trim()) {
    return body.error.trim();
  }
  return fallback;
}
