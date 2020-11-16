package de.samply.bbmri.auth.rest;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A public key that can be used to verify signatures.
 */
@XmlRootElement
public class OAuth2Key implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The key type, usually this is either RSA or EC.
   */
  private String keyType;

  /**
   * The usage of this key.
   */
  private String use;

  /**
   * The keys ID.
   */
  private String keyId;

  /**
   * The RSA modulus.
   */
  private String modulesN;

  /**
   * The RSA public exponent.
   */
  private String exponent;

  /**
   * The Base64URL encoded DER formatted public key.
   */
  private String derFormat;

  /**
   * The Base64 encoded DER formatted public key.
   */
  private String base64DerFormat;

  /**
   * {@link #keyType}.
   *
   * @return a {@link java.lang.String} object.
   */
  @XmlElement(name = "kty")
  public String getKeyType() {
    return keyType;
  }

  /**
   * {@link #keyType}.
   *
   * @param keyType a {@link java.lang.String} object.
   */
  public void setKeyType(String keyType) {
    this.keyType = keyType;
  }

  /**
   * {@link #use}.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getUse() {
    return use;
  }

  /**
   * {@link #use}.
   *
   * @param use a {@link java.lang.String} object.
   */
  public void setUse(String use) {
    this.use = use;
  }

  /**
   * {@link #keyId}.
   *
   * @return a {@link java.lang.String} object.
   */
  @XmlElement(name = "kid")
  public String getKeyId() {
    return keyId;
  }

  /**
   * {@link #keyId}.
   *
   * @param keyId a {@link java.lang.String} object.
   */
  public void setKeyId(String keyId) {
    this.keyId = keyId;
  }

  /**
   * {@link #modulesN}.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getModulesN() {
    return modulesN;
  }

  /**
   * {@link #modulesN}.
   *
   * @param modulesN a {@link java.lang.String} object.
   */
  public void setModulesN(String modulesN) {
    this.modulesN = modulesN;
  }

  /**
   * {@link #exponent}.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getExponent() {
    return exponent;
  }

  /**
   * {@link #exponent}.
   *
   * @param exponent a {@link java.lang.String} object.
   */
  public void setExponent(String exponent) {
    this.exponent = exponent;
  }

  /**
   * {@link #derFormat}.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getDerFormat() {
    return derFormat;
  }

  /**
   * {@link #derFormat}.
   *
   * @param derFormat a {@link java.lang.String} object.
   */
  public void setDerFormat(String derFormat) {
    this.derFormat = derFormat;
  }

  /**
   * Todo.
   * @return the base64DerFormat
   */
  public String getBase64DerFormat() {
    return base64DerFormat;
  }

  /**
   * Todo.
   * @param base64DerFormat the base64DerFormat to set
   */
  public void setBase64DerFormat(String base64DerFormat) {
    this.base64DerFormat = base64DerFormat;
  }

}
