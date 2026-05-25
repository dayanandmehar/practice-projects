package org.example.productcatalogservice_feb2026.repos;

import org.example.productcatalogservice_feb2026.models.Category;
import org.example.productcatalogservice_feb2026.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductRepo productRepo;

    //@Test
    @Transactional
    public void testFetchTypeAndMode() {
        Optional<Category> categoryOptional = categoryRepo.findById(2L);
        Category category = categoryOptional.get();
        for(Product product : category.getProducts()) {
            System.out.println(product.getName());
        }
    }


    @Test
    @Transactional
    public void testNPlusOneProblem() {
        for(Category category : categoryRepo.findAll()) {
            System.out.println(category.getName());
            for(Product product : category.getProducts()) {
                System.out.println(product.getName());
            }
        }
    }

    @Test
    @Transactional
    public void testCustomQuery() {
        Optional<Product> optionalProduct = productRepo.findById(1L);
        Product product = optionalProduct.get();
//        System.out.println(product.getCategory().getName());
        //System.out.println(productRepo.getCategoryNameCorrespondingToProductId(1L));
       // System.out.println(productRepo.getCategoryNameCorrespondingToProduct(product));
    }



}