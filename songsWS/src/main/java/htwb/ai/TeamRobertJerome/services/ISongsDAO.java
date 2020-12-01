package htwb.ai.TeamRobertJerome.services;

import htwb.ai.TeamRobertJerome.model.Songs;

import java.util.List;

public interface ISongsDAO {

    public Songs getSong(int id) throws Exception;

    public List<Songs> getAllSongs() throws Exception;

    public int postSong(Songs song) throws Exception;
}
