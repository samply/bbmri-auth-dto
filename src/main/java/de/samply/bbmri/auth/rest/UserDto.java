package de.samply.bbmri.auth.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a User registered in Samply Auth.
 *
 */
@XmlRootElement(name = "user")
// Ignores the extra attributes but mapping JSON to this java class
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * The users real name.
   */
  private String name;

  /**
   * The users email.
   */
  private String email;

  /**
   * The OpenID Connect subject.
   */
  private String sub;

  /**
   * The phone number of the user.
   */
  private String phone;

  /**
   * Group names (collections) of a bbmri user.
   */
  private List<String> groupNames;

  //region properties

  /**
   * Todo.
   * @return the realName
   */
  public String getName() {
    return name;
  }

  /**
   * Todo.
   * @param realName the realName to set
   */
  public void setName(String realName) {
    this.name = realName;
  }

  /**
   * Todo.
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Todo.
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  public String getSub() {
    return sub;
  }

  public void setSub(String sub) {
    this.sub = sub;
  }

  public List<String> getGroupNames() {
    return groupNames;
  }

  public void setGroupNames(List<String> groupNames) {
    this.groupNames = groupNames;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
  //endregion
}
