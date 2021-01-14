package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.Songs;
import TeamRobertJerome.main.repository.SongsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SongsService implements ISongsService {

  @Autowired
  private SongsRepository repository;

  @Override
  public ArrayList<Songs> findAll() {
    return (ArrayList<Songs>) repository.findAll();
  }

  @Override
  public Songs getSong(Integer id) {
    return repository.findById(id).get();
  }

  @Override
  public int postSong(Songs song) throws Exception {
    try{
      repository.save(song);
      return song.getId();
    }catch (Exception e){
      throw new Exception("only title is allowed null");
    }

  }
}
