package de.samply.bbmri.auth.client.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import de.samply.common.config.OAuth2Client;
import java.io.Serializable;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;

/**
 * The abstract client-side Jwt used to verify a serialized Jwt using the providers public key.
 */
public abstract class AbstractJwt implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The serialized Jwt.
   */
  private final String serialized;

  /**
   * If true the signature is valid.
   */
  private boolean signatureValid = false;

  /**
   * The Jwt claims set defined in this Jwt.
   */
  private transient JWTClaimsSet claimsSet;

  /**
   * The public key used to verify the JWTs signature.
   */
  private PublicKey publicKey;

  /**
   * Initializes this Jwt with an OAuth2Client configuration and the serialized string.
   * The public key is loaded from the base64 format in the OAuth2Client configuration.
   *
   * @param config     the OAuth2 client side configuration. The public key is needed to check the
   *                   signature.
   * @param serialized the serialized Jwt
   * @throws JwtException if any error occurs during deserialization
   *                                                      or signature verification
   */
  protected AbstractJwt(OAuth2Client config, String serialized) throws JwtException {
    this(KeyLoader.loadKey(config.getHostPublicKey()), serialized);
  }

  /**
   * Initializes this Jwt with a public key and the serialized string.
   * The public key is used to check the signature.
   *
   * @param publicKey  The identity providers public key (needed to check the signature)
   * @param serialized the serialized Jwt
   * @throws JwtException if any error occurs during deserialization
   *                                                      or signature verification
   */
  protected AbstractJwt(PublicKey publicKey, String serialized) throws JwtException {
    this.serialized = serialized;
    this.publicKey = publicKey;

    try {
      reloadClaimsSet();
    } catch (ParseException e) {
      throw new JwtParseException(e);
    } catch (JOSEException e) {
      throw new JwtInvalidSignatureFormatException();
    }
  }

  /**
   * Reloads the claims set. This is necessary, because the JWTClaimsSet is not Serializable.
   * Deserializes the serialized token again and gets the claims set.
   *
   * @throws ParseException Todo
   * @throws JOSEException Todo
   * @throws JwtKeyMissmatchException Todo
   */
  private void reloadClaimsSet() throws ParseException, JOSEException, JwtKeyMissmatchException {
    Base64URL[] parsedParts = JWTParser.parse(serialized).getParsedParts();
    SignedJWT signed = new SignedJWT(parsedParts[0], parsedParts[1], parsedParts[2]);

    JWSVerifier verifier;

    JWSAlgorithm algorithm = signed.getHeader().getAlgorithm();
    boolean isRsa = algorithm == JWSAlgorithm.RS256 || algorithm == JWSAlgorithm.RS384
        || algorithm == JWSAlgorithm.RS512;

    boolean isEC = algorithm == JWSAlgorithm.ES256 || algorithm == JWSAlgorithm.ES384
        || algorithm == JWSAlgorithm.ES512;

    if (publicKey instanceof RSAPublicKey && isRsa) {
      verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
    } else if (publicKey instanceof ECPublicKey && isEC) {
      ECPublicKey ecPublicKey = (ECPublicKey) publicKey;
      verifier = new ECDSAVerifier(ecPublicKey);
    } else {
      throw new JwtKeyMissmatchException();
    }

    signatureValid = signed.verify(verifier);

    claimsSet = signed.getJWTClaimsSet();
  }

  /**
   * Checks if this Jwt is valid. The signature is checked once, the expiration date is checked
   * every time this method is called.
   *
   * @return a boolean.
   */
  public boolean isValid() {
    Date now = new Date();
    if (getClaimsSet().getExpirationTime() != null) {
      signatureValid = signatureValid && now.before(getClaimsSet().getExpirationTime());
    }

    if (getClaimsSet().getNotBeforeTime() != null) {
      signatureValid = signatureValid && now.after(getClaimsSet().getNotBeforeTime());
    }
    return signatureValid;
  }

  /**
   * Returns all claims.
   *
   * @return a {@link com.nimbusds.jwt.JWTClaimsSet} object.
   */
  public JWTClaimsSet getClaimsSet() {
    if (claimsSet == null) {
      try {
        reloadClaimsSet();
      } catch (Exception e) {
        return null;
      }
    }
    return claimsSet;
  }

  /**
   * Returns the serialized string of this Jwt.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getSerialized() {
    return serialized;
  }

  /**
   * Returns the subject. This should be used as an user ID.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getSubject() {
    return getClaimsSet().getSubject();
  }

  /**
   * Returns the public key that has been used to verify this Jwt.
   *
   * @return the publicKey
   */
  public PublicKey getPublicKey() {
    return publicKey;
  }

  /**
   * This method must return one of the currently defined token types.
   *
   * <pre>
   * ACCESS_TOKEN
   * ID_TOKEN
   * REFRESH_TOKEN
   * </pre>
   *
   * @return typ of the token
   */
  protected abstract String getTokenType();

}
