package de.samply.bbmri.auth.rest;

/**
 * A scope is a permission that will be included in the access token.
 */
public enum Scope {

  OPENID("openid"), EMAIL("email"), PROFILE("profile"), PHONE("phone"), GROUPNAMES(
      "groupNames"), OFFLINE_ACCESS("offline_access");

  private final String identifier;

  private Scope(String identifier) {
    this.identifier = identifier;
  }

  /**
   * Get the identifier.
   * @return the identifier
   */
  public String getIdentifier() {
    return identifier;
  }

}
