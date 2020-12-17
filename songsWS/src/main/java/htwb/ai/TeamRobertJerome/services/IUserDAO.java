package htwb.ai.TeamRobertJerome.services;

import htwb.ai.TeamRobertJerome.model.User;
import javassist.NotFoundException;

public interface IUserDAO {


  User getUserByUserId(String userId) throws NotFoundException;
}
