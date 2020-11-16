package de.samply.bbmri.auth.client.jwt;

import de.samply.bbmri.auth.client.jwt.JwtVocabulary.TokenType;
import de.samply.bbmri.auth.rest.RoleDto;
import de.samply.common.config.OAuth2Client;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import net.minidev.json.JSONObject;

/**
 * The client side Jwt OpenID Token. Checks the signature and validity of a serialized OpenID ID
 * Token Jwt. Contains for example the users real name, his email address, his address, and other
 * fields.
 */
public class JwtIdToken extends AbstractJwt {

  private static final long serialVersionUID = 1L;

  /**
   * The client ID used to get this ID token. It is used to verify the audience.
   */
  private final String clientId;

  /**
   * The list of roles that the user is a member of.
   */
  private List<RoleDto> roles = new ArrayList<>();

  /**
   * {@link AbstractJwt#AbstractJwt(OAuth2Client, String)}.
   *
   * @param config     the OAuth2 client side configuration. The public key is needed to check the
   *                   signature.
   * @param serialized the serialized Jwt
   * @throws JwtException if any error occurs during deserialization
   *                                                      or signature verification
   */
  public JwtIdToken(OAuth2Client config, String serialized) throws JwtException {
    this(config.getClientId(), KeyLoader.loadKey(config.getHostPublicKey()), serialized);
  }

  /**
   * {@link AbstractJwt#AbstractJwt(PublicKey, String)}.
   *
   * @param clientId   Your client ID
   * @param publicKey  The IdP's public key
   * @param serialized the serialized ID token
   * @throws JwtException JwtException
   */
  public JwtIdToken(String clientId, PublicKey publicKey, String serialized) throws JwtException {
    super(publicKey, serialized);
    this.clientId = clientId;

    Object rolesClaim = getClaimsSet().getClaim(JwtVocabulary.ROLES);

    if (rolesClaim instanceof List<?>) {
      List<?> list = (List<?>) rolesClaim;

      for (Object o : list) {
        if (o instanceof JSONObject) {
          JSONObject json = (JSONObject) o;
          RoleDto role = new RoleDto();
          role.setIdentifier((String) json.get(JwtVocabulary.ROLE_IDENTIFIER));
          role.setDescription((String) json.get(JwtVocabulary.ROLE_DESCRIPTION));
          role.setName((String) json.get(JwtVocabulary.ROLE_NAME));
          roles.add(role);
        }
      }
    }
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public boolean isValid() {
    return super.isValid() && getClaimsSet().getAudience().contains(clientId);
  }

  /**
   * Returns a list of roles.
   *
   * @return list of roles
   */
  public List<RoleDto> getRoles() {
    return roles;
  }

  @Override
  protected String getTokenType() {
    return TokenType.ID_TOKEN;
  }

}
