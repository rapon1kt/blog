type RegisterViaApiInput = {
  username: string;
  email: string;
  password: string;
};

type RegisterViaApiResult = {
  ok: boolean;
  message?: string;
};

export async function registerViaApi(
  input: RegisterViaApiInput,
): Promise<RegisterViaApiResult> {
  const response = await fetch("/api/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(input),
  });

  const data = (await response.json().catch(() => ({}))) as { message?: string };

  return {
    ok: response.ok,
    message: data.message,
  };
}
