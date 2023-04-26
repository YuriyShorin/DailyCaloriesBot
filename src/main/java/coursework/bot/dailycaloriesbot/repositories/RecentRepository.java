package coursework.bot.dailycaloriesbot.repositories;

import coursework.bot.dailycaloriesbot.entities.Recent;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecentRepository extends CrudRepository<Recent, Long> {
    Optional<Recent> findByProduct(String product);

}
