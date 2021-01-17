package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.Songs;
import javassist.NotFoundException;

import java.util.ArrayList;

public interface ISongsService {
  ArrayList<Songs> findAll() throws NotFoundException;

  Songs getSong(Integer id) throws NotFoundException;

  int postSong(Songs song) throws Exception;

  void putSong(Integer id, Songs song) throws NotFoundException;

  void deleteSong(Integer id) throws NotFoundException;
}
