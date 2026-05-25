package org.example.productcatalogservice_feb2026.services;

import org.example.productcatalogservice_feb2026.models.Product;
import org.example.productcatalogservice_feb2026.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ProductRepo productRepo;

    public Page<Product> searchProducts(String query, Integer pageSize, Integer pageNumber) {
        Sort sortByPrice = Sort.by("price");
        Sort sortByIdDesc = Sort.by("id").descending();
        Sort finalSort = sortByPrice.and(sortByIdDesc);
      return productRepo.findProductByName(query, PageRequest.of(pageNumber,pageSize,finalSort));
    }
}

/*
{
	"query" : "laptop",
	"pageSize" : 3,
	"pageNumber" : 1,
	"sort" : [
		{
			"sortType" : "ASC",
			"sortCriteria" : "price"
		},
		{
			"sortType" : "DESC",
			"sortCriteria" : "id"
		},
		{
				"sortType" : "ASC",
			 "sortCriteria" : "title"
		}
	]
}

 */