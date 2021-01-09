package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.User;
import javassist.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

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

  @Override
  public User getUserByToken(String token) throws NotFoundException {
    em = factory.createEntityManager();
    User reqUser = null;
    ArrayList<User> userList = (ArrayList<User>) em.createQuery("SELECT a FROM ouruser a", User.class).getResultList();
    em.close();
    for (User tempUser : userList) {
      if(tempUser.getToken().equals(token)){
        reqUser = tempUser;
        break;
      }
    }
    if(reqUser==null){
      throw new NotFoundException("Kein User mit diesem Token");
    }
    return reqUser;
  }

  @Override
  public boolean isTokenValid(String token){
    em = factory.createEntityManager();
    User reqUser = null;
    ArrayList<User> userList = (ArrayList<User>) em.createQuery("SELECT a FROM ouruser a", User.class).getResultList();
    em.close();
    for (User tempUser : userList) {
      if(tempUser.getToken().equals(token)){
        reqUser = tempUser;
        break;
      }
    }
    return  reqUser != null ? true : false;
  }
}
