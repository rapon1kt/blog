"use client";
import { SessionProvider } from "next-auth/react";

interface AuthProps {
  children: React.ReactNode;
}

export default function AuthProvider({ children }: AuthProps) {
  return (
    <SessionProvider refetchInterval={60} refetchOnWindowFocus>
      {children}
    </SessionProvider>
  );
}
