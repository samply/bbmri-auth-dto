package de.samply.bbmri.auth.rest;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This Token information DTO represents all necessary informations about an access token. Lets the
 * identity provider check the access tokens signature.
 */
@XmlRootElement
public class TokenInfoDto implements Serializable {


  private static final long serialVersionUID = 1L;

  /**
   * The tokens expiration date. The token is invalid, if the expiration is in the past.
   */
  private long expirationDate;

  /**
   * The user ID. This is usually a URL of the issuer and an integer.
   */
  private String subject;

  /**
   * The list of scopes this access token may be used for.
   */
  private List<String> scope;

  /**
   * A random string. Ignore this.
   */
  private String jti;

  /**
   * The date before which the access token is not valid.
   */
  private long notBefore;

  /**
   * The date at which the access token was issued.
   */
  private long issuedAt;

  /**
   * The URL of the issuer.
   */
  private String issuer;

  /**
   * {@link #expirationDate}.
   *
   * @return a long.
   */
  public long getExpirationDate() {
    return expirationDate;
  }

  /**
   * {@link #expirationDate}.
   *
   * @param expirationDate a long.
   */
  public void setExpirationDate(long expirationDate) {
    this.expirationDate = expirationDate;
  }

  /**
   * {@link #subject}.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getSubject() {
    return subject;
  }

  /**
   * {@link #subject}.
   *
   * @param subject a {@link java.lang.String} object.
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * {@link #scope}.
   *
   * @return a {@link java.util.List} object.
   */
  public List<String> getScope() {
    return scope;
  }

  /**
   * {@link #scope}.
   *
   * @param scope a {@link java.util.List} object.
   */
  public void setScope(List<String> scope) {
    this.scope = scope;
  }

  /**
   * {@link #jti}.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getJti() {
    return jti;
  }

  /**
   * {@link #jti}.
   *
   * @param nonce a {@link java.lang.String} object.
   */
  public void setJti(String nonce) {
    this.jti = nonce;
  }

  /**
   * {@link #notBefore}.
   *
   * @return a long.
   */
  public long getNotBefore() {
    return notBefore;
  }

  /**
   * {@link #notBefore}.
   *
   * @param notBefore a long.
   */
  public void setNotBefore(long notBefore) {
    this.notBefore = notBefore;
  }

  /**
   * {@link #issuedAt}.
   *
   * @return a long.
   */
  public long getIssuedAt() {
    return issuedAt;
  }

  /**
   * {@link #issuedAt}.
   *
   * @param issuedAt a long.
   */
  public void setIssuedAt(long issuedAt) {
    this.issuedAt = issuedAt;
  }

  /**
   * {@link #issuer}.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getIssuer() {
    return issuer;
  }

  /**
   * {@link #issuer}.
   *
   * @param issuer a {@link java.lang.String} object.
   */
  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }

}
