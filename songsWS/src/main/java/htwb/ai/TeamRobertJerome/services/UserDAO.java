package htwb.ai.TeamRobertJerome.services;

import htwb.ai.TeamRobertJerome.model.User;
import javassist.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserDAO implements IUserDAO {

  private String PERSISTENCE_UNIT_NAME = "songdb";
  EntityManager em;
  EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

  public void setPERSISTENCE_UNIT_NAME(String name) { PERSISTENCE_UNIT_NAME = name; }

  @Override
  public User getUserByUserId(String userId) throws NotFoundException {
    em = factory.createEntityManager();
    User user = em.find(User.class, userId);
    em.close();

    if(user==null){
      throw new NotFoundException("Kein User mit dieser ID!");
    }else{
      return user;
    }
  }
}
