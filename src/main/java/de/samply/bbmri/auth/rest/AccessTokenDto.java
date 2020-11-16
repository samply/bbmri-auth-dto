package de.samply.bbmri.auth.rest;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The access token data transfer object. Has three different tokens:
 *
 * <pre>
 * - access token (must be used to use the APIs from other modules)
 * - id token (must be used to get the users identity, e.g. his name)
 * - refresh token (must be used to get a new valid access token if necessary).
 * </pre>
 */
@XmlRootElement
public class AccessTokenDto implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
   * This token type must be "Bearer" because the implicit flows are not supported in Samply Auth.
   */
  private final String tokenType = "Bearer";
  private String refreshToken;
  /**
   * The OAuth2 access token. In Samply.Auth this is a Jwt, signed with the identity providers
   * private key. This access token contains information about the scopes this access token was
   * issued for, the user ID (called subject in OAuth2 terminology) and other fields.
   */
  private String accessToken;
  /**
   * The OAuth2 OpenID token. In Samply.Auth this is a Jwt, signed with the identity providers
   * private key. This token contains various informations about the user, like his name or his
   * email.
   */
  private String idToken;
  /**
   * The number of seconds that the access token is valid.
   */
  private int expiresIn = 0;

  /**
   * The scopes this token is valid for.
   */
  private String scope;

  @XmlElement(name = "refresh_token")
  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @XmlElement(name = "scope")
  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  @XmlElement(name = "access_token")
  public String getAccessToken() {
    return accessToken;
  }

  /**
   * {@link #accessToken}.
   *
   * @param accessToken a {@link java.lang.String} object.
   */
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  /**
   * {@link #idToken}.
   *
   * @return a {@link java.lang.String} object.
   */
  @XmlElement(name = "id_token")
  public String getIdToken() {
    return idToken;
  }

  /**
   * {@link #idToken}.
   *
   * @param idToken a {@link java.lang.String} object.
   */
  public void setIdToken(String idToken) {
    this.idToken = idToken;
  }

  /**
   * Returns the string that must be used in HTTP request headers.
   *
   * @return a {@link java.lang.String} object.
   */
  @XmlTransient
  public String getHeader() {
    return tokenType + " " + accessToken;
  }

  /**
   * Todo.
   * @return the tokenType
   */
  @XmlElement(name = "token_type")
  public String getTokenType() {
    return tokenType;
  }

  /**
   * Todo.
   * @return the expiresIn
   */
  @XmlElement(name = "expires_in")
  public int getExpiresIn() {
    return expiresIn;
  }

  /**
   * Todo.
   * @param expiresIn the expiresIn to set
   */
  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }

}
