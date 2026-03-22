export function getBackendBaseUrl(): string {
  const url = process.env.NEXT_PUBLIC_BACKEND_URL?.replace(/\/$/, "");
  if (!url) {
    throw new Error("NEXT_PUBLIC_BACKEND_URL is not set.");
  }
  return url;
}

export type BackendErrorBody = {
  message?: string;
  error?: string;
  status?: number;
};

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
