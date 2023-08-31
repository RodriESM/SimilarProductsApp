package com.between.test.similar.controllers;

import com.between.test.similar.apiclient.client.ApiException;
import com.between.test.similar.apiclient.client.model.ProductDetail;
import com.between.test.similar.services.ProductServiceImpl;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @GetMapping("/{productId}/similar")
    @ApiResponse(
            responseCode = "200",
            description = "Products found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Similar products not found"
    )
    public ResponseEntity<List<ProductDetail>> getProductsSimilar(@PathVariable String productId) {
        try {
            List<ProductDetail> similarProducts = productServiceImpl.getAllSimilarProducts(productId);

            if (similarProducts.isEmpty()) {
                return ResponseEntity.notFound().build(); // Return 404 if no similar products found
            }

            return ResponseEntity.ok(similarProducts);
        } catch (ApiException e) {
            return handleSimilarProducts(e);
        }
    }

    private ResponseEntity<List<ProductDetail>> handleSimilarProducts(ApiException e) {
        logger.error("Error occurred while handling similar products", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
