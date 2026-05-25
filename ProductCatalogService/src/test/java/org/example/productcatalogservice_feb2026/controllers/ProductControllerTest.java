package org.example.productcatalogservice_feb2026.controllers;

import org.example.productcatalogservice_feb2026.dtos.ProductDto;
import org.example.productcatalogservice_feb2026.models.Product;
import org.example.productcatalogservice_feb2026.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private IProductService productService;

    @Test
    public void TestGetProductById_WithValidId_ReturnsProductSuccessfully() {
        //Arrange
        Long productId = 1L;

        Product product = new Product();
        product.setId(1L);

        when(productService.getProductById(productId)).thenReturn(product);

        //Act
        ResponseEntity<ProductDto> responseEntity = productController.getProductById(productId);

        //Assert
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(productId,responseEntity.getBody().getId());
    }

    @Test
    public void TestGetProductById_WithNegativeId_ResultsInIllegalArgumentException() {
        //Arrange
        Long productId = -1L;

        //Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> productController.getProductById(productId));

        assertEquals("INVALID PRODUCT ID",exception.getMessage());
    }

    @Test
    public void TestGetProductById_WhereProductServiceReturnsNullProduct_ResultsInRuntimeException()
    {
        //Arrange
        Long productId = 99L;
        when(productService.getProductById(productId)).thenReturn(null);

        //Act and Assert
        assertThrows(RuntimeException.class,() -> productController.getProductById(productId));
    }
}