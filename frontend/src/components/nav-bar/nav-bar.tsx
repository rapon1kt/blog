"use client";

import { signOut, useSession } from "next-auth/react";
import { useState } from "react";
import "./style.css";

export default function NavBar() {
  const { data: session } = useSession();
  const [imgOk, setImgOk] = useState(true);

  const picture = session?.user?.picture;
  const username = session?.user?.username ?? "Account";

  return (
    <header className="navbar">
      <div className="navbar-brand">
        <span className="navbar-logo">Blog</span>
      </div>
      <div className="nav-content">
        <nav>
          <ul className="nav-links">
            <li className="nav-link nav-link--active">Home</li>
            <li className="nav-link">Explore</li>
            <li className="nav-link">Search</li>
          </ul>
        </nav>
        <div className="nav-profile">
          <button
            type="button"
            className="nav-avatar-btn"
            title="Sign out"
            onClick={() => signOut({ callbackUrl: "/" })}
          >
            {picture && imgOk ? (
              <img
                src={picture}
                alt=""
                className="profile-picture"
                onError={() => setImgOk(false)}
              />
            ) : (
              <span className="profile-fallback" aria-hidden>
                {username.slice(0, 1).toUpperCase()}
              </span>
            )}
          </button>
        </div>
      </div>
    </header>
  );
}
