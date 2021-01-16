package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.User;
import javassist.NotFoundException;

public interface IUserService {
  User getUserByUserId(String userId) throws NotFoundException;

  void setToken(User user, String token) throws NotFoundException;

  boolean isTokenValid(String token);

    User getUserByUserToken(String token) throws NotFoundException;
}
