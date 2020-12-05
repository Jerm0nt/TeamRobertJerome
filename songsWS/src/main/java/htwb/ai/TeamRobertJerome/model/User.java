package htwb.ai.TeamRobertJerome.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
  private String userId;
  private String password;

  @Id
  public String getUserId() {
    return userId;
    }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }
}
