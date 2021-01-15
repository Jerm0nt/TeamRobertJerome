package TeamRobertJerome.main.repository;

import TeamRobertJerome.main.model.Songs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongListRepository extends CrudRepository<Songs, Integer> {
}
