package org.example.productcatalogservice_feb2026.services;

import org.example.productcatalogservice_feb2026.models.Product;

import java.util.List;

public interface IProductService {

    Product getProductById(Long id);

    Product replaceProduct(Long id, Product product);

    Product addProduct(Product product);

    void deleteProduct(Long id);

    List<Product> getAllProducts();
}
