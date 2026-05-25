package org.example.productcatalogservice_feb2026.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.productcatalogservice_feb2026.dtos.ProductDto;
import org.example.productcatalogservice_feb2026.models.Product;
import org.example.productcatalogservice_feb2026.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerMvcTest {

    @MockBean
    private IProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void TestGetAllProducts_RunSuccessfully() throws Exception {
        //////THIS IS FOR MOCKING/////
        Product product = new Product();
        product.setId(1L);
        product.setName("Iphone");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Macbook");

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);
        when(productService.getAllProducts()).thenReturn(productList);
        ///////////////////////////////


        ////// FOR ASSERTING/EXPECTING /////
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Iphone");


        ProductDto productDto2 = new ProductDto();
        productDto2.setId(2L);
        productDto2.setName("Macbook");

        List<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(productDto);
        productDtos.add(productDto2);
        ////////////////////////////////

        //java object <-->  JSON <--> String
        String response = objectMapper.writeValueAsString(productDtos);


        mockMvc.perform(get("/products"))    // Act
                .andExpect(status().isOk())           // Assert
                .andExpect(content().string(response));  //Assert

        // This is happening at line 73 --> assertEquals(response,content().toString());

    }
}


//[
// {
//    "id" : 1, "name" : "iphone"
//        },
//        {
//            "id" : 2, "name" : "iphone12"
//        }
//        ]