package TeamRobertJerome.main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Songs {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String title;

  private String artist;

  private String label;

  private int released;

  public Songs(){}

  public Songs( int id, String title){
    this.id  = id;
    this.title = title;
  }

  public int getId(){
    return id;
  }

  public String getTitle(){
    return title;
  }
  public void setTitle(String title){
    this.title=title;
  }

  public String getArtist(){
    return artist;
  }
  public void setArtist(String artist){
    this.artist=artist;
  }

  public String getLabel(){
    return label;
  }
  public void setLabel(String label){
    this.label=label;
  }

  public int getReleased(){
    return released;
  }

  public void setReleased(int released){
    this.released=released;
  }
}
