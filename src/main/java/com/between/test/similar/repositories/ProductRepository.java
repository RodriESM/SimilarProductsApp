package com.between.test.similar.repositories;

import com.between.test.similar.apiclient.client.ApiException;
import com.between.test.similar.apiclient.client.model.ProductDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository {
    List<ProductDetail> getAllSimilarProducts(String productId) throws ApiException;
}
