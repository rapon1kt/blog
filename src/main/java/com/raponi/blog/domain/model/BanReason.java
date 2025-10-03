package com.raponi.blog.domain.model;

public enum BanReason {
  // Category: Inappropriate or Illegal Content
  HATE_SPEECH(
      BanCategory.INAPPROPRIATE_CONTENT,
      "Content that promotes or incites violence, discrimination, or hatred based on protected characteristics."),
  VIOLENT_OR_EXPLICIT_CONTENT(
      BanCategory.INAPPROPRIATE_CONTENT,
      "Publication of content that glorifies violence, extreme cruelty, or is shocking and gratuitous."),
  PROMOTION_OF_ILLEGAL_ACTIVITIES(
      BanCategory.INAPPROPRIATE_CONTENT,
      "Promotion, instruction, or encouragement of illegal acts, such as drug use, terrorism, or self-harm."),
  SEXUALLY_EXPLICIT_CONTENT(
      BanCategory.INAPPROPRIATE_CONTENT,
      "Publication of nudity or sexual acts not permitted by community guidelines."),
  HARMFUL_MISINFORMATION(
      BanCategory.INAPPROPRIATE_CONTENT,
      "Deliberate dissemination of false information that may cause public harm or panic."),

  // Category: Abusive Behavior and Harassment
  HARASSMENT_AND_STALKING(
      BanCategory.ABUSIVE_BEHAVIOR,
      "Directing repetitive and unwanted attacks at one or more individuals."),
  THREATS_AND_INCITEMENT_TO_VIOLENCE(
      BanCategory.ABUSIVE_BEHAVIOR,
      "Directly threatening an individual or group with physical violence."),
  BULLYING_AND_INTIMIDATION(
      BanCategory.ABUSIVE_BEHAVIOR,
      "Behavior intended to maliciously humiliate, intimidate, or attack an individual."),
  DOXXING(
      BanCategory.ABUSIVE_BEHAVIOR,
      "Publishing someone's private information without their consent (e.g., address, phone number)."),

  // Category: Spam and Platform Manipulation
  SPAM(
      BanCategory.SPAM_AND_MANIPULATION,
      "Repetitive posting of irrelevant content, links, or unsolicited advertising."),
  PHISHING_AND_SCAMS(
      BanCategory.SPAM_AND_MANIPULATION,
      "Attempts to deceive users to obtain sensitive information (passwords, financial data)."),
  MULTI_ACCOUNT_ABUSE(
      BanCategory.SPAM_AND_MANIPULATION,
      "Using fake accounts to manipulate engagement, harass users, or circumvent a previous ban."),
  FLOODING(
      BanCategory.SPAM_AND_MANIPULATION,
      "Posting excessive content in a short period to disrupt the user experience."),

  // Category: Intellectual Property and Identity
  COPYRIGHT_INFRINGEMENT(
      BanCategory.INTELLECTUAL_PROPERTY,
      "Publishing content that belongs to someone else without proper permission or credit."),
  IMPERSONATION(
      BanCategory.INTELLECTUAL_PROPERTY,
      "Pretending to be another person, brand, or organization to deceive other users."),

  // Category: General Terms Violations
  REPEATED_TERMS_OF_SERVICE_VIOLATION(
      BanCategory.GENERAL_VIOLATIONS,
      "The user continues to violate platform rules despite previous warnings."),
  SYSTEM_ABUSE(
      BanCategory.GENERAL_VIOLATIONS,
      "Improper use of platform features, such as abusing the report system.");

  private final BanCategory category;
  private final String description;

  BanReason(BanCategory category, String description) {
    this.category = category;
    this.description = description;
  }

  public BanCategory getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }
}
