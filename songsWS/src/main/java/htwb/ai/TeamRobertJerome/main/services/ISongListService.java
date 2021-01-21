package htwb.ai.TeamRobertJerome.main.services;

import htwb.ai.TeamRobertJerome.main.model.SongList;
import javassist.NotFoundException;

public interface ISongListService {
    int postSongList(SongList songList, String token) throws NotFoundException;

  SongList getSongList(Integer id) throws NotFoundException;

    boolean deleteSongList(Integer id, String token) throws NotFoundException;
}
