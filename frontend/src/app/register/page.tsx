"use client";

import Link from "next/link";
import React from "react";
import { signIn } from "next-auth/react";
import { useRouter } from "next/navigation";
import "../auth.css";

interface MessageType {
  text: string;
  type: "error" | "success";
}

export default function RegisterPage() {
  const router = useRouter();
  const [username, setUsername] = React.useState("");
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [confirm, setConfirm] = React.useState("");
  const [pending, setPending] = React.useState(false);
  const [message, setMessage] = React.useState<MessageType>();

  const handleRegister = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setMessage(undefined);

    if (password !== confirm) {
      setMessage({ text: "Passwords do not match.", type: "error" });
      return;
    }

    setPending(true);

    const res = await fetch("/api/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        username: username.trim(),
        email: email.trim(),
        password,
      }),
    });

    const data = (await res.json().catch(() => ({}))) as { message?: string };

    if (!res.ok) {
      setPending(false);
      setMessage({
        text: data.message ?? "Registration failed.",
        type: "error",
      });
      return;
    }

    setMessage({
      text: "Account created. Signing you in…",
      type: "success",
    });

    const signInResult = await signIn("credentials", {
      username: username.trim(),
      password,
      redirect: false,
    });

    setPending(false);

    if (signInResult?.ok) {
      router.push("/home");
      router.refresh();
      return;
    }

    setMessage({
      text: "Account created. Sign in with your new credentials.",
      type: "success",
    });
  };

  return (
    <div className="auth-page auth-page--has-bg-image">
      <div className="auth-panel">
        <div className="auth-panel-inner">
          <p className="auth-brand">Blog</p>
          <h1 className="auth-title">Create an account</h1>
          <p className="auth-subtitle">
            Username (3+ characters), a valid email, and password (8+
            characters).
          </p>
          <form className="auth-form" onSubmit={handleRegister} noValidate>
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
                minLength={3}
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
              Email
              <input
                className="auth-input"
                required
                autoComplete="email"
                type="email"
                name="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="you@example.com"
                disabled={pending}
              />
            </label>
            <label className="auth-label">
              Password
              <input
                className="auth-input"
                required
                minLength={8}
                autoComplete="new-password"
                type="password"
                name="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="At least 8 characters"
                disabled={pending}
              />
            </label>
            <label className="auth-label">
              Confirm password
              <input
                className="auth-input"
                required
                minLength={8}
                autoComplete="new-password"
                type="password"
                name="confirm"
                value={confirm}
                onChange={(e) => setConfirm(e.target.value)}
                placeholder="Repeat password"
                disabled={pending}
              />
            </label>
            <button className="auth-submit" type="submit" disabled={pending}>
              {pending ? "Creating account…" : "Sign up"}
            </button>
          </form>
          <p className="auth-footer">
            Already have an account?{" "}
            <Link className="auth-link" href="/">
              Sign in
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}
