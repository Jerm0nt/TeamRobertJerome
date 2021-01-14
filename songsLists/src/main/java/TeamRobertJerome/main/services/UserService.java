package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.User;
import TeamRobertJerome.main.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  @Autowired
  private UserRepository repository;

  @Override
  public User getUserByUserId(String userId) throws NotFoundException {
    try{
      User user = repository.findById(userId).get();
      return user;
    }catch (Exception e){
      throw new NotFoundException("Kein User mit dieser ID!");
    }
  }


}
