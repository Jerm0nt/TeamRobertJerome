package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.User;
import TeamRobertJerome.main.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

  @Override
  public void setToken(User user, String token) throws NotFoundException {
    try{
      User oldUser = repository.findById(user.getUserId()).get();
      oldUser.setToken(token);
      repository.save(oldUser);
    }catch (Exception e){
      throw new NotFoundException("Kein solcher User!");
    }
  }

  @Override
  public boolean isTokenValid(String token){
    ArrayList<User> userList = (ArrayList<User>) repository.findAll();
    for (User u: userList){
      if(u.getToken()!=null) {
        if (u.getToken().equals(token)) {
          return true;
        }
      }
    }
    return false;
  }

}
