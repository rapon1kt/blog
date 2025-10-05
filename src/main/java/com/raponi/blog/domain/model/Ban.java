package com.raponi.blog.domain.model;

import java.time.Instant;

public class Ban {
  private String id;
  private boolean active;
  private BanCategory category;
  private BanReason reason;
  private String banDescription;
  private String moderatorDescription;
  private String moderatorId;
  private String bannedId;
  private Instant expiresAt;
  private Instant bannedAt;

  public Ban(BanCategory category, BanReason reason, String moderatorDescription, String moderatorId, String bannedId,
      Instant expiresAt) {
    this.active = true;
    this.category = category;
    this.reason = reason;
    this.banDescription = reason.getDescription();
    this.moderatorDescription = moderatorDescription;
    this.moderatorId = moderatorId;
    this.bannedId = bannedId;
    this.expiresAt = expiresAt;
    this.bannedAt = Instant.now();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public BanCategory getCategory() {
    return category;
  }

  public void setCategory(BanCategory category) {
    this.category = category;
  }

  public BanReason getReason() {
    return reason;
  }

  public void setReason(BanReason reason) {
    this.reason = reason;
  }

  public String getBanDescription() {
    return banDescription;
  }

  public void setBanDescription(String banDescription) {
    this.banDescription = banDescription;
  }

  public String getModeratorDescription() {
    return moderatorDescription;
  }

  public void setModeratorDescription(String moderatorDescription) {
    this.moderatorDescription = moderatorDescription;
  }

  public String getModeratorId() {
    return moderatorId;
  }

  public void setModeratorId(String moderatorId) {
    this.moderatorId = moderatorId;
  }

  public String getBannedId() {
    return bannedId;
  }

  public void setBannedId(String bannedId) {
    this.bannedId = bannedId;
  }

  public Instant getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Instant expiresAt) {
    this.expiresAt = expiresAt;
  }

  public Instant getBannedAt() {
    return bannedAt;
  }

  public void setBannedAt(Instant bannedAt) {
    this.bannedAt = bannedAt;
  }

}
