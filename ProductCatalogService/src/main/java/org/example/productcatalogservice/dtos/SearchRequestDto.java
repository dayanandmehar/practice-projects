package org.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchRequestDto {
    private String  query;
    private Integer pageSize;
    private Integer pageNumber;
}
