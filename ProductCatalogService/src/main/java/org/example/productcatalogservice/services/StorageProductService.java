package org.example.productcatalogservice.services;

import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.models.Status;
import org.example.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class StorageProductService implements IProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product getProductById(Long id) {
        Optional<Product> productOptional =  productRepo.findById(id);
        if(productOptional.isPresent()) {
            return productOptional.get();
        }

        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        Optional<Product> productOptional =  productRepo.findById(id);
        if(productOptional.isPresent()) {
            product.setId(id);
            product.setLastUpdatedAt(new Date());
            return productRepo.save(product);
        }

        throw new RuntimeException("Product with id "+ id + " not available");
    }

    @Override
    public Product addProduct(Product product) {
        if (product.getId() == null || productRepo.findById(product.getId()).isEmpty()) {
            product.setStatus(Status.ACTIVE);
            product.setCreatedAt(new Date());
            product.setLastUpdatedAt(new Date());
            return productRepo.save(product);
        }
        throw new IllegalArgumentException("Product with id " + product.getId() + " already exists");
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> productOptional =  productRepo.findById(id);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            if(product.getStatus().equals(Status.ACTIVE)) {
                product.setStatus(Status.DELETED);
                product.setLastUpdatedAt(new Date());
                productRepo.save(product);
            } else {
                productRepo.deleteById(id);
            }
        }
        else {
            throw new RuntimeException("Product with id" + id + " not available");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll().stream()
                .filter(product -> product.getStatus() == Status.ACTIVE)
                .toList();
    }
}
