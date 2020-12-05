package htwb.ai.TeamRobertJerome.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserDAO implements IUserDAO {

  private String PERSISTENCE_UNIT_NAME = null;
  EntityManager em;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

  public void setPERSISTENCE_UNIT_NAME(String name) { PERSISTENCE_UNIT_NAME = name; }
}
