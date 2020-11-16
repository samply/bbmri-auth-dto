package de.samply.bbmri.auth.rest;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An object that identifies a key either by its ID or by its SHA512 hashed DER formatted public
 * key.
 */
@XmlRootElement
public class KeyIdentificationDto implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The key ID you got when the public key has been registred.
   */
  private int keyId;

  /**
   * The public keys fingerprint. Use the SHA-512 hash on the DER formated public key.
   */
  private String sha512Hash;

  /**
   * {@link #sha512Hash}.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getSha512Hash() {
    return sha512Hash;
  }

  /**
   * {@link #sha512Hash}.
   *
   * @param sha512Hash a {@link java.lang.String} object.
   */
  public void setSha512Hash(String sha512Hash) {
    this.sha512Hash = sha512Hash;
  }

  /**
   * {@link #keyId}.
   *
   * @return a int.
   */
  @XmlElement(name = "key_id")
  public int getKeyId() {
    return keyId;
  }

  /**
   * {@link #keyId}.
   *
   * @param keyId a int.
   */
  public void setKeyId(int keyId) {
    this.keyId = keyId;
  }

}
