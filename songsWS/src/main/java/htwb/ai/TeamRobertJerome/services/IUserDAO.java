package htwb.ai.TeamRobertJerome.services;

import htwb.ai.TeamRobertJerome.model.User;

public interface IUserDAO {


  User getUserByUserId(String userId);
}
