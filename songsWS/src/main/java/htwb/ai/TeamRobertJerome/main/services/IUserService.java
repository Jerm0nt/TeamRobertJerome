package htwb.ai.TeamRobertJerome.main.services;

import htwb.ai.TeamRobertJerome.main.model.SongList;
import htwb.ai.TeamRobertJerome.main.model.User;
import javassist.NotFoundException;

import java.util.Set;

public interface IUserService {
  User getUserByUserId(String userId) throws NotFoundException;

  void setToken(User user, String token) throws NotFoundException;

  boolean isTokenValid(String token);

  User getUserByToken(String token) throws NotFoundException;

  Set<SongList> getSongListSet(String userId, String token) throws NotFoundException;

  void deleteSongListFromUser(User userByToken, SongList songList);
}
