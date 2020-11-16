package de.samply.bbmri.auth.client.jwt;

/**
 * Thrown when the signature has an invalid format.
 */
public class JwtInvalidSignatureFormatException extends JwtException {

  private static final long serialVersionUID = 1L;

  /**
   * <p>Constructor for JwtInvalidSignatureFormatException.</p>
   */
  public JwtInvalidSignatureFormatException() {
    super("The signature format is invalid!");
  }

}
