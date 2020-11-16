package de.samply.bbmri.auth.client.jwt;

/**
 * Thrown when the providers key can not be used to verify a signature (e.g. when the Jwt is signed
 * using RSA and SHAx, but the key is an elliptic curve key).
 */
public class JwtKeyMissmatchException extends JwtException {

  private static final long serialVersionUID = 1L;

  /**
   * <p>Constructor for JwtKeyMissmatchException.</p>
   */
  public JwtKeyMissmatchException() {
    super("The key can not be used to verify this kind of signature!");
  }

}
