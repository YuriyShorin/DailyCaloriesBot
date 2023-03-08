package coursework.bot.dailycaloriesbot.controllers;

import coursework.bot.dailycaloriesbot.entities.Products;
import coursework.bot.dailycaloriesbot.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductsController {

    @Autowired
    ProductsRepository productsRepository;

    public Products getProduct(String product) {
        Optional<Products> productsData = productsRepository.findByProduct(product);
        return productsData.orElse(null);
    }

    public List<Products> getProducts(String product) {
        return productsRepository.findByProductLike(product);
    }
}
