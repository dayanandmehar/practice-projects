package org.example.productcatalogservice.services;

import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.models.Status;
import org.example.productcatalogservice.repos.ProductRepo;
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
      int safePageSize = pageSize == null ? 20 : Math.min(Math.max(pageSize, 1), 100);
      int safePageNumber = pageNumber == null ? 0 : Math.max(pageNumber, 0);
      String safeQuery = query == null ? "" : query.trim();
      return productRepo.findByNameContainingIgnoreCaseAndStatus(
              safeQuery, Status.ACTIVE, PageRequest.of(safePageNumber, safePageSize, finalSort));
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
