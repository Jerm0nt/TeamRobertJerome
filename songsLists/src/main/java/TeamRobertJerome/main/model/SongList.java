package TeamRobertJerome.main.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class SongList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int songListId;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  private String name;
  boolean isPrivate;
  @ManyToMany
  private Set<Songs> songs;

  public int getSongListId() {
    return songListId;
  }

  public void setSongListId(int id) {
    this.songListId = id;
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

  public Set<Songs> getSongs() {
    return songs;
  }

  public void setSongs(Set<Songs> songList) {
    this.songs = songList;
  }
}
