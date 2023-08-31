package com.between.test.similar.apiclient.client.api;

import com.between.test.similar.apiclient.client.model.ProductDetail;
import com.between.test.similar.apiclient.client.model.SimilarProducts;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
/**
 * API tests for DefaultApi
 */
public class DefaultApiTest {

    @Mock
    private DefaultApi defaultApi;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetProductProductId() throws Exception {
        String productId = "1";
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(productId);

        when(defaultApi.getProductProductId(any())).thenReturn(productDetail);

        ProductDetail result = defaultApi.getProductProductId(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());

        verify(defaultApi).getProductProductId(any());
    }

    @Test
    public void testGetProductSimilarids() throws Exception {
        String productId = "1";
        SimilarProducts similarProducts = new SimilarProducts();
        similarProducts.add("2");
        similarProducts.add("3");

        when(defaultApi.getProductSimilarids(any())).thenReturn(similarProducts);

        SimilarProducts result = defaultApi.getProductSimilarids(productId);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(defaultApi).getProductSimilarids(any());
    }
}
