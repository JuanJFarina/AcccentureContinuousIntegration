package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import com.mockcompany.webapp.services.SearchService; // Import the SearchService class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class SearchController {

    private final SearchService searchService; // Declare the SearchService field

    @Autowired
    public SearchController(SearchService searchService, ProductItemRepository productItemRepository) {
        this.searchService = searchService;
        this.productItemRepository = productItemRepository;
    }

    /**
     * This is a instance field.  It is provided by the spring framework through the constructor because of the
     * @Autowired annotation.  Autowire tells the spring framework to automatically find and use an instance of
     * the declared class when creating this class.
     */
    private final ProductItemRepository productItemRepository;

    @Autowired
    public SearchController(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    /**
     * The search method, annotated with @GetMapping telling spring this method should be called
     * when an HTTP GET on the path /api/products/search is made.  A single query parameter is declared
     * using the @RequestParam annotation.  The value that is passed when performing a query will be
     * in the query parameter.
     * @param query
     * @return The filtered products
     */
    @GetMapping("/api/products/search")
    public Collection<ProductItem> search(@RequestParam("query") String query) {
        Iterable<ProductItem> allItems = this.productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();

        for (ProductItem item : allItems) {
            // Check if the item matches the query parameter
            if (item.getName().toLowerCase().contains(query.toLowerCase()) || item.getDescription().toLowerCase().contains(query.toLowerCase())) {
                itemList.add(item);
            }
        }

        return itemList;
    }
}
