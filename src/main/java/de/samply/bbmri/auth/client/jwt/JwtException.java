package de.samply.bbmri.auth.client.jwt;

/**
 * Thrown when an exception occurs while verifying a signature or deserializing a Jwt.
 */
public class JwtException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * <p>Constructor for JwtException.</p>
   *
   * @param string a {@link java.lang.String} object.
   */
  public JwtException(String string) {
    super(string);
  }

  /**
   * <p>Constructor for JwtException.</p>
   *
   * @param e a {@link java.lang.Throwable} object.
   */
  public JwtException(Throwable e) {
    super(e);
  }

}
