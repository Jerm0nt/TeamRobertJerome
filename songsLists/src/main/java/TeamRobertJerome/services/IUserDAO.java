package TeamRobertJerome.services;


import TeamRobertJerome.model.User;
import javassist.NotFoundException;

public interface IUserDAO {

  User getUserByUserId(String userId) throws NotFoundException;

  public User getUserByToken(String token) throws NotFoundException;

  public boolean isTokenValid(String token);

}
