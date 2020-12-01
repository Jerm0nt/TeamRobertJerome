package htwb.ai.TeamRobertJerome.services;

import htwb.ai.TeamRobertJerome.model.Songs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
}
