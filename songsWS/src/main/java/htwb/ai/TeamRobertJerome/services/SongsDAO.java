package htwb.ai.TeamRobertJerome.services;

import htwb.ai.TeamRobertJerome.model.Songs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SongsDAO {
  private static final String PERSISTENCE_UNIT_NAME = "songdb";
  EntityManager em;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);


  public Songs getSong(int id) throws Exception {
    em = factory.createEntityManager();

    Songs song = em.find(Songs.class, id);
    em.close();
    if(song == null){
      throw new Exception("Kein Song "+id+" gefunden!");
    }else {
      return song;
    }
  }
}
