package TeamRobertJerome.main.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class SongList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  private String name;
  boolean isPrivate;

  @ManyToMany
  List<Songs> songs;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isPrivate() {
    return isPrivate;
  }

  public void setPrivate(boolean isPublic) {
    this.isPrivate = isPublic;
  }

  public List<Songs> getSongs() {
    return songs;
  }

  public void setSongs(List<Songs> songList) {
    this.songs = songList;
  }
}
