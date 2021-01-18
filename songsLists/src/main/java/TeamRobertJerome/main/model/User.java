package TeamRobertJerome.main.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

  @Id
  private String userId;

  @OneToMany(mappedBy = "user",
    cascade = CascadeType.ALL)
  private Set<SongList> songLists = new HashSet<>();

  public Set<SongList> getSongLists() {
    return songLists;
  }

  public void setSongLists(Set<SongList> songLists) {
    this.songLists = songLists;

    for(SongList s : songLists){
      s.setUser(this);
    }
  }

  private String password;
  private String firstName;
  private String lastName;
  private String token;

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Id
  public String getUserId() {
    return userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
