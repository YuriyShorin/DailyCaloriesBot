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

    public Products getProductById(int id) {
        Optional<Products> usersData = productsRepository.findById(id);
        return usersData.orElse(null);
    }

    public Products getProductByName(String name) {
        Optional<Products> productsData = productsRepository.findByProduct(name);
        return productsData.orElse(null);
    }

    public List<Products> getProductsByName(String product) {
        return productsRepository.findByProductLike(product);
    }
}

