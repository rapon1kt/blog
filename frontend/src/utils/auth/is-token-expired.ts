const TOKEN_EXPIRATION_SKEW_MS = 30_000;

export function isTokenExpired(exp?: number): boolean {
  if (typeof exp !== "number") {
    return true;
  }

  return Date.now() >= exp * 1000 - TOKEN_EXPIRATION_SKEW_MS;
}
