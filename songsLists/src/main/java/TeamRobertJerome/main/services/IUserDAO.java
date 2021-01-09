package TeamRobertJerome.main.services;


import TeamRobertJerome.main.model.User;
import javassist.NotFoundException;

public interface IUserDAO {

  User getUserByUserId(String userId) throws NotFoundException;

  public User getUserByToken(String token) throws NotFoundException;

  public boolean isTokenValid(String token);

}
