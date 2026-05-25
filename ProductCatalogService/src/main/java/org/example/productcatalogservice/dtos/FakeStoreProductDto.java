package org.example.productcatalogservice_feb2026.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FakeStoreProductDto {
    private String title;
    private String description;
    private String category;
    private Double price;
    private Long id;
    private String image;
}

