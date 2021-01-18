package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.SongList;
import TeamRobertJerome.main.model.Songs;
import TeamRobertJerome.main.model.User;
import TeamRobertJerome.main.repository.SongListRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongListService implements ISongListService {
  @Autowired
  IUserService userService;
  @Autowired
  ISongsService songsService;
  @Autowired
  SongListRepository repository;


  @Override
  public int postSongList(SongList songList, String token) throws NotFoundException {
    List<Songs> songs = songList.getSongs();
    if(songsService.areSongsValid(songs)) {
      User owner = userService.getUserByToken(token);
      songList.setUser(owner);
      repository.save(songList);
      return songList.getId();
    }else{
      throw new NotFoundException("Diese Songs sind teilweise nicht in der DB!");
    }
  }

  @Override
  public SongList getSongList(Integer id) throws NotFoundException {
    try{
      SongList songList = repository.findById(id).get();
      return songList;
    }catch(Exception e){
      throw new NotFoundException("songList nicht gefunden");
    }
  }

  @Override
  public boolean deleteSongList(Integer id, String token) throws NotFoundException {
    try{
      if(repository.findById(id).get().getUser().getToken().equals(token)){
        repository.deleteById(id);
        return true;
      }else{
        return false;
      }
    }catch(Exception e){
      throw new NotFoundException("Songliste nicht gefunden!");
    }
  }
}
