package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.User;
import javassist.NotFoundException;

public interface IUserService {
  User getUserByUserId(String userId) throws NotFoundException;
}
