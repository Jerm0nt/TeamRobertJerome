package TeamRobertJerome.main.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name="song_list")
public class SongList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne(optional = false)
  @JoinColumn(name = "owner_id")
  private User Owner;
  private String name;
  boolean isPublic;
  @ManyToMany
  private Set<Songs> songList;

}
