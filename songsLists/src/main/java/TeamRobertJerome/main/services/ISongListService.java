package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.SongList;
import javassist.NotFoundException;

public interface ISongListService {
    int postSongList(SongList songList, String token) throws NotFoundException;
}
