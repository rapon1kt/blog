"use client";

import Link from "next/link";
import React from "react";
import { signIn } from "next-auth/react";
import { useRouter } from "next/navigation";
import "./auth.css";

interface MessageType {
  text: string;
  type: "error" | "success";
}

function signInErrorMessage(error: string | null | undefined): string {
  if (!error || error === "CredentialsSignin") {
    return "Invalid username or password.";
  }
  try {
    return decodeURIComponent(error);
  } catch {
    return error;
  }
}

export default function Login() {
  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [pending, setPending] = React.useState(false);
  const [message, setMessage] = React.useState<MessageType>();

  const router = useRouter();

  const handleLogin = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setPending(true);
    setMessage(undefined);

    const result = await signIn("credentials", {
      username: username.trim(),
      password,
      redirect: false,
    });

    setPending(false);

    if (result?.ok) {
      setMessage({
        text: "Signed in. Redirecting…",
        type: "success",
      });
      router.push("/home");
      router.refresh();
      return;
    }

    setMessage({
      text: signInErrorMessage(result?.error),
      type: "error",
    });
  };

  return (
    <div className="auth-page auth-page--has-bg-image">
      <div className="auth-panel">
        <div className="auth-panel-inner">
          <p className="auth-brand">Blog</p>
          <h1 className="auth-title">Welcome back</h1>
          <p className="auth-subtitle">
            Sign in with your username and password.
          </p>

          <form className="auth-form" onSubmit={handleLogin} noValidate>
            {message && (
              <div
                className={`auth-alert auth-alert--${message.type}`}
                role="alert"
              >
                {message.text}
              </div>
            )}

            <label className="auth-label">
              Username
              <input
                className="auth-input"
                required
                autoComplete="username"
                type="text"
                name="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="Username"
                disabled={pending}
              />
            </label>

            <label className="auth-label">
              Password
              <input
                className="auth-input"
                required
                autoComplete="current-password"
                type="password"
                name="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="••••••••"
                disabled={pending}
              />
            </label>

            <button className="auth-submit" type="submit" disabled={pending}>
              {pending ? "Signing in…" : "Sign in"}
            </button>
          </form>

          <p className="auth-footer">
            Don&apos;t have an account?{" "}
            <Link className="auth-link" href="/register">
              Create one
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}
