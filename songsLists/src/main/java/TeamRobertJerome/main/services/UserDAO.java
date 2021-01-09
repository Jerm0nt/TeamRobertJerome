package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.User;
import javassist.NotFoundException;


public class UserDAO implements IUserDAO {



  @Override
  public User getUserByUserId(String userId) throws NotFoundException {
    return new User("jerome","eble");
  }

  @Override
  public User getUserByToken(String token) throws NotFoundException {

    User reqUser = null;

    return reqUser;
  }

  @Override
  public boolean isTokenValid(String token){
    User reqUser = null;

    return  reqUser != null ? true : false;
  }
}
