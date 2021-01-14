package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.Songs;
import TeamRobertJerome.main.repository.SongsRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
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

  @Override
  public void putSong(Integer id, Songs song) throws NotFoundException {
    if(song.getId()!=id || song.getTitle()==null){
      throw new InvalidParameterException("Die übergebenen Parameter sind nicht gültig!");
    }
    try{
      Songs oldSong = repository.findById(id).get();
      oldSong.setArtist(song.getArtist());
      oldSong.setTitle(song.getTitle());
      oldSong.setLabel(song.getLabel());
      oldSong.setReleased(song.getReleased());

      repository.save(oldSong);
    }catch(Exception e){
      throw new NotFoundException("Song mit id existiert nicht");
    }


  }

  @Override
  public void deleteSong(Integer id) throws NotFoundException {
    try{
      repository.deleteById(id);
    }catch(Exception e){
      e.printStackTrace();
      throw new NotFoundException("song mit id existiert nicht");
    }
  }
}
