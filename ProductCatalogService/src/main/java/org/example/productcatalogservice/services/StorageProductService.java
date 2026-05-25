package org.example.productcatalogservice_feb2026.services;

import org.example.productcatalogservice_feb2026.models.Product;
import org.example.productcatalogservice_feb2026.models.Status;
import org.example.productcatalogservice_feb2026.repos.ProductRepo;
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
            return productRepo.save(product);
        }

        throw new RuntimeException("Product with id "+ id + " not available");
    }

    @Override
    public Product addProduct(Product product) {
        Optional<Product> productOptional =  productRepo.findById(product.getId());
        if(productOptional.isEmpty()) {
            return productRepo.save(product);
        }

        // We can throw an exception also that ProductAlreadyFound
        return productOptional.get();
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
        return productRepo.findAll();
    }
}
