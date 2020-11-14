import javax.persistence.*;

@Entity
@Table(name="songs")
public class Songs {
    private int id;
    private String title;
    private String artist;
    private String label;
    private int released;

    public Songs(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId(){
        return id;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public void setId(int id){
    }

    @Column(name = "title")
    public String getTitle(){
        return title;
    }
    @Column(name = "title")
    public void setTitle(String title){
        this.title=title;
    }

    @Column(name = "artist")
    public String getArtist(){
        return artist;
    }
    @Column(name = "artist")
    public void setArtist(String artist){
        this.artist=artist;
    }

    @Column(name = "label")
    public String getLabel(){
        return label;
    }
    @Column(name = "label")
    public void setLabel(String label){
        this.label=label;
    }

    @Column(name = "released")
    public int getReleased(){
        return released;
    }
    @Column(name = "released")
    public void setReleased(int released){
        this.released=released;
    }
}
