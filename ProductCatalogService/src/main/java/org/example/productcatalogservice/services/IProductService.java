package org.example.productcatalogservice.services;

import org.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {

    Product getProductById(Long id);

    Product replaceProduct(Long id, Product product);

    Product addProduct(Product product);

    void deleteProduct(Long id);

    List<Product> getAllProducts();
}
