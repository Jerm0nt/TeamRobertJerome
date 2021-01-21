package htwb.ai.TeamRobertJerome.main.repository;

import htwb.ai.TeamRobertJerome.main.model.Songs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongsRepository extends CrudRepository<Songs, Integer> {
}
