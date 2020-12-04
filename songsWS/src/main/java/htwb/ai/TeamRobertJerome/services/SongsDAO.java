package htwb.ai.TeamRobertJerome.services;

import htwb.ai.TeamRobertJerome.model.Songs;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.security.InvalidParameterException;
import java.util.List;

public class SongsDAO implements ISongsDAO{
  private String PERSISTENCE_UNIT_NAME = null;
  EntityManager em;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

  public void setPERSISTENCE_UNIT_NAME(String name){
    PERSISTENCE_UNIT_NAME = name;
  }

  @Override
  public Songs getSong(int id) throws Exception {
    em = factory.createEntityManager();

    Songs song = em.find(Songs.class, id);
    em.close();
    if(song == null){
      throw new Exception("Kein Song "+id+" gefunden! getSong");
    }else {
      return song;
    }
  }

  @Override
  public List<Songs> getAllSongs() throws Exception{
    em = factory.createEntityManager();
    List<Songs> songs = em.createQuery("SELECT a FROM Songs a", Songs.class).getResultList();
    em.close();
    if( songs == null){
      throw new Exception ("Keine song gefunden getAllSongs");
    }
    else{
      return songs;
    }
  }

  @Override
  public int postSong(Songs song) throws Exception{
    try {
      em = factory.createEntityManager();
      em.getTransaction().begin();
      em.persist(song);
      em.getTransaction().commit();
      return song.getId();
    }
    catch (Exception e){
      throw new Exception("only title is allowed null");
    }
  }

  @Override
  public void putSong(int id, Songs song)throws InvalidParameterException, Exception {
    try{
      if(song.getId()==id) {
        em.getTransaction().begin();
        em.merge(song);
        em.getTransaction().commit();
        em.close();
      }
      else{
        throw new InvalidParameterException("ID ungleich Songid");
      }
    }
    catch (Exception e){
      throw new Exception("mmmm");
    }
  }


}
