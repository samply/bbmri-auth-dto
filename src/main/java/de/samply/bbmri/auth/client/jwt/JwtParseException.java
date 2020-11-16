package de.samply.bbmri.auth.client.jwt;

/**
 * Thrown when the Jwt can not be parsed (e.g. it does not consist of three parts separated by a
 * dot).
 */
public class JwtParseException extends JwtException {

  private static final long serialVersionUID = 1L;

  /**
   * <p>Constructor for JwtParseException.</p>
   *
   * @param e a {@link java.lang.Throwable} object.
   */
  public JwtParseException(Throwable e) {
    super(e);
  }

}
