package com.raponi.blog.domain.model;

public enum BanCategory {
  INAPPROPRIATE_CONTENT("Inappropriate or Illegal Content"),
  ABUSIVE_BEHAVIOR("Abusive Behavior and Harassment"),
  SPAM_AND_MANIPULATION("Spam and Platform Manipulation"),
  INTELLECTUAL_PROPERTY("Intellectual Property and Identity"),
  GENERAL_VIOLATIONS("General Terms Violations");

  private final String displayName;

  BanCategory(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
