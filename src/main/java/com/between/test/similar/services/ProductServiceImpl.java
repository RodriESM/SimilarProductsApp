package com.between.test.similar.services;

import com.between.test.similar.apiclient.client.ApiException;
import com.between.test.similar.apiclient.client.api.DefaultApi;
import com.between.test.similar.apiclient.client.model.ProductDetail;
import com.between.test.similar.apiclient.client.model.SimilarProducts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final DefaultApi defaultApi;

    @Autowired
    public ProductServiceImpl(DefaultApi defaultApi) {
        this.defaultApi = defaultApi;
    }

    public List<ProductDetail> getAllSimilarProducts(String productId) throws ApiException {
        try {
            SimilarProducts similarProductsList = defaultApi.getProductSimilarids(productId);

            if (similarProductsList == null || similarProductsList.isEmpty()) {
                return Collections.emptyList();
            }

            return similarProductsList.stream()
                    .map(similarProduct -> {
                        try {
                            return defaultApi.getProductProductId(similarProduct);
                        } catch (ApiException e) {
                            logger.error("Error fetching product details for ID: {}", similarProduct, e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparing(ProductDetail::getId))
                    .collect(Collectors.toList());

        } catch (ApiException e) {
            logger.error("Error occurred while fetching similar product IDs for ID: {}", productId, e);
            return Collections.emptyList();
        } catch (Exception e) {
            logger.error("An unexpected error occurred", e);
            return Collections.emptyList();
        }
    }
}
