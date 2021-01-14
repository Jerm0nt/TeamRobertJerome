package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.Songs;
import javassist.NotFoundException;

import java.util.ArrayList;

public interface ISongsService {
  ArrayList<Songs> findAll();

  Songs getSong(Integer id);

  int postSong(Songs song) throws Exception;

  void putSong(Integer id, Songs song) throws NotFoundException;

  void deleteSong(Integer id) throws NotFoundException;
}
