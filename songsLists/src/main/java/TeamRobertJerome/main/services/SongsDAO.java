package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.Songs;
import javassist.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class SongsDAO implements ISongsDAO{


  @Override
  public Songs getSong(int id) throws Exception {
    return new Songs(id,"wrecking ball");
  }

  @Override
  public List<Songs> getAllSongs() throws Exception{

    List<Songs> songs = new ArrayList<>();
    songs.add(new Songs(1,"hello"));
    songs.add(new Songs(2,"welcome"));

      return songs;

  }

  @Override
  public int postSong(Songs song) throws Exception{
    return 2;
  }

  @Override
  public void putSong(int id, Songs song)throws InvalidParameterException, NotFoundException {





  }

  @Override
  public void deleteSong(int id) throws NotFoundException {
  }
}
