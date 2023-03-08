package coursework.bot.dailycaloriesbot.repositories;

import coursework.bot.dailycaloriesbot.entities.Products;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository extends CrudRepository<Products, Integer> {
    Optional<Products> findByProduct(String product);
    @Query("select p from Products p " +
            "where lower(p.product) like :product")
    List<Products> findByProductLike(@Param("product") String product);
}