package com.between.test.similar.services;

import com.between.test.similar.apiclient.client.ApiException;
import com.between.test.similar.apiclient.client.model.ProductDetail;

import java.util.List;

public interface ProductService {
    List<ProductDetail> getAllSimilarProducts(String productId) throws ApiException;
}
