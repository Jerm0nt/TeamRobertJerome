package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.Songs;

import java.util.ArrayList;

public interface ISongsService {
  ArrayList<Songs> findAll();

  Songs getSong(Integer id);

  int postSong(Songs song) throws Exception;
}
