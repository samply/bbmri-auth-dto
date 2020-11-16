package de.samply.bbmri.auth.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by paul on 27.10.16.
 */
@XmlRootElement
// Ignores the extra attributes but mapping JSON to this java class
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListDto implements Serializable {

  private static final long serialVersionUID = -4439834854837306186L;

  @XmlElement
  private Collection<UserDto> users;

  public Collection<UserDto> getUsers() {
    return users;
  }

  public void setUsers(Collection<UserDto> users) {
    this.users = users;
  }
}
