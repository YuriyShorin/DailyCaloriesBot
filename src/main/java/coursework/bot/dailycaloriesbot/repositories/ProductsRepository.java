package coursework.bot.dailycaloriesbot.repositories;

import coursework.bot.dailycaloriesbot.entities.Products;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface ProductsRepository extends CrudRepository<Products, Integer> {
    Optional<Products> findByProduct(String product);
}