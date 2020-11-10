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

    @Column(name = "title")
    public String getTitle(){
        return title;
    }

    @Column(name = "artist")
    public String getArtist(){
        return artist;
    }

    @Column(name = "label")
    public String getLabel(){
        return label;
    }

    @Column(name = "released")
    public int getReleased(){
        return released;
    }
}
