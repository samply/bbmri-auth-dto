package de.samply.bbmri.auth.client.jwt;

import de.samply.bbmri.auth.client.jwt.JwtVocabulary.TokenType;
import de.samply.common.config.OAuth2Client;
import java.security.PublicKey;
import java.util.List;

/**
 * The client side Jwt access token. Checks the signature and validity of a serialized Jwt. Contains
 * informations about access control. Use this token in the Authorization Header in HTTP requests.
 */
public class JwtAccessToken extends AbstractJwt {

  private static final long serialVersionUID = 1L;

  /**
   * {@link AbstractJwt#AbstractJwt(PublicKey, String)}.
   *
   * @param key        The identity providers public key (needed to check the signature)
   * @param serialized the serialized Jwt
   * @throws JwtException if any error occurs during deserialization or signature verification
   */
  public JwtAccessToken(PublicKey key, String serialized) throws JwtException {
    super(key, serialized);
    init();
  }

  /**
   * {@link AbstractJwt#AbstractJwt(OAuth2Client, String)}.
   *
   * @param config     the OAuth2 client side configuration. The public key is needed to check the
   *                   signature.
   * @param serialized the serialized Jwt
   * @throws JwtException if any error occurs during deserialization
   *                                                      or signature verification
   */
  public JwtAccessToken(OAuth2Client config, String serialized) throws JwtException {
    super(config, serialized);
    init();
  }

  /**
   * Todo.
   * @throws JwtException JwtException
   */
  private void init() throws JwtException {
  }

  /**
   * Returns all scopes this access token was issued for.
   *
   * @return the list of scopes
   */
  @SuppressWarnings("unchecked")
  public List<String> getScopes() {
    return (List<String>) getClaimsSet().getClaim(JwtVocabulary.SCOPE);
  }

  /**
   * Checks if there this is an extended access token.
   *
   * @return if the access token is extended
   */
  public boolean isExtended() {
    return getClaimsSet().getClaim(JwtVocabulary.KEY) != null;
  }

  /**
   * Returns the included state from the request.
   *
   * @return the included state
   */
  public String getState() {
    return (String) getClaimsSet().getClaim(JwtVocabulary.STATE);
  }

  /**
   * Returns the string that must be used in the "Authorization" header.
   *
   * @return Bearer + token
   */
  public String getHeader() {
    return "Bearer " + getSerialized();
  }

  @Override
  protected String getTokenType() {
    return TokenType.ACCESS_TOKEN;
  }

}
