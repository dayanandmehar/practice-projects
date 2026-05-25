package org.example.productcatalogservice_feb2026.controllers;

import org.example.productcatalogservice_feb2026.dtos.CategoryDto;
import org.example.productcatalogservice_feb2026.dtos.ProductDto;
import org.example.productcatalogservice_feb2026.models.Category;
import org.example.productcatalogservice_feb2026.models.Product;
import org.example.productcatalogservice_feb2026.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController   //Because I want spring to create bean(singleton object) of ProductController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

//    public ProductController(IProductService productService) {
//        this.productService = productService;
//    }


    //To Get Product Details By Id
    @GetMapping("{id}")  //Variables are specified in braces
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        if(productId == 0) {
            throw new IllegalArgumentException("Please pass productId greater than 0");
           // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if(productId < 0) {
            throw new IllegalArgumentException("INVALID PRODUCT ID");
        }

       Product product = productService.getProductById(productId);
       if(product == null) throw new RuntimeException("Product is not available");
       ProductDto  productDto = from(product);
       ResponseEntity<ProductDto> responseEntity =
               new ResponseEntity<>(productDto, HttpStatus.OK);
       return responseEntity;

    }

    @PutMapping("{id}")
    public ProductDto replaceProduct(@PathVariable Long id, @RequestBody ProductDto input) {
       Product inputProduct = from(input);
       Product output = productService.replaceProduct(id, inputProduct);
       return from(output);
    }


    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto product) {
        Product input = from(product);
        Product response = productService.addProduct(input);
        return from(response);
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            productDtos.add(from(product));
        }

        return productDtos;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    private ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setId(product.getId());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setDescription(product.getCategory().getDescription());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setId(product.getCategory().getId());
            productDto.setCategory(categoryDto);
        }

        return productDto;
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setName(productDto.getCategory().getName());
            category.setId(productDto.getCategory().getId());
            product.setCategory(category);
        }
        return product;
    }

//www.google.com/products/100

}


//ControllerA       ServiceA             RepoA
//
//                                       RepoB
// ControllerB     ServiceB             ---
//                                      ---
//                ServiceC
//                                       ---
//  ---
//
//                ServiceD
//   ---
//
//
//  ---