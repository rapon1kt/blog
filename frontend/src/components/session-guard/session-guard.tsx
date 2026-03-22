"use client";
import { signOut, useSession } from "next-auth/react";
import { useEffect } from "react";

export default function SessionGuard({
  children,
}: {
  children: React.ReactNode;
}) {
  const { data: session } = useSession();
  useEffect(() => {
    if (session?.error === "TokenExpired") {
      signOut({ callbackUrl: "/" });
    }
  }, [session]);

  return <>{children}</>;
}
