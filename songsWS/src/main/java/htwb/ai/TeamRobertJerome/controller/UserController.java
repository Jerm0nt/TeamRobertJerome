package htwb.ai.TeamRobertJerome.controller;

import htwb.ai.TeamRobertJerome.services.IUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/auth")
public class UserController {

  IUserDAO userDAOImpl;

  @Autowired
  public UserController(IUserDAO userDAO){ userDAOImpl = userDAO; }


}
