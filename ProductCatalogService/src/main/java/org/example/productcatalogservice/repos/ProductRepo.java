package org.example.productcatalogservice_feb2026.repos;

import org.example.productcatalogservice_feb2026.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
        Page<Product> findProductByName(String query, Pageable pageable);

        Optional<Product> findById(Long id);

        List<Product> findAll();

        Product save(Product product);

        void deleteById(Long id);

        List<Product> findProductByPriceBetween(Double low, Double high);

       // List<Product> findAllOrderByPrice(); WRONG SYNTAX
        List<Product> findAllByOrderByPrice();

       @Query("SELECT c.name from Product p join Category c on p.category.id=c.id where p.id=?1")
       String getCategoryNameCorrespondingToProductId(Long id);

       // Will error out
//    @Query("SELECT c.name from Product p join Category c on p.category.id=c.id where p.id=:product")
//    String getCategoryNameCorrespondingToProduct(Product product);

}
