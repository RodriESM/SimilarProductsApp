package com.between.test.similar.services;

import com.between.test.similar.apiclient.client.ApiException;
import com.between.test.similar.apiclient.client.api.DefaultApi;
import com.between.test.similar.apiclient.client.model.ProductDetail;
import com.between.test.similar.apiclient.client.model.SimilarProducts;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private DefaultApi defaultApi;

    private ProductServiceImpl productServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productServiceImpl = new ProductServiceImpl(defaultApi);
    }

    @Test
    public void testGetAllSimilarProducts_Success() throws ApiException {
        String productId = "123";
        SimilarProducts similarProductsList = new SimilarProducts();
        similarProductsList.add("456");
        similarProductsList.add("789");

        when(defaultApi.getProductSimilarids(productId)).thenReturn(similarProductsList);

        ProductDetail productDetail1 = new ProductDetail();
        productDetail1.setId("456");
        productDetail1.setName("Product B");
        productDetail1.setPrice(BigDecimal.valueOf(20.0));
        productDetail1.setAvailability(true);

        ProductDetail productDetail2 = new ProductDetail();
        productDetail2.setId("789");
        productDetail2.setName("Product A");
        productDetail2.setPrice(BigDecimal.valueOf(30.0));
        productDetail2.setAvailability(true);

        when(defaultApi.getProductProductId("456")).thenReturn(productDetail1);
        when(defaultApi.getProductProductId("789")).thenReturn(productDetail2);

        List<ProductDetail> expected = Arrays.asList(productDetail1,productDetail2);

        List<ProductDetail> result = productServiceImpl.getAllSimilarProducts(productId);
        assertEquals(expected, result);

        verify(defaultApi, times(1)).getProductSimilarids(productId);
        verify(defaultApi, times(1)).getProductProductId("456");
        verify(defaultApi, times(1)).getProductProductId("789");
    }

    @Test
    public void testGetAllSimilarProducts_EmptySimilarProducts() throws ApiException {
        String productId = "123";
        SimilarProducts similarProductsList = new SimilarProducts();

        when(defaultApi.getProductSimilarids(productId)).thenReturn(similarProductsList);

        List<ProductDetail> result = productServiceImpl.getAllSimilarProducts(productId);
        assertEquals(Collections.emptyList(), result);

        verify(defaultApi, times(1)).getProductSimilarids(productId);
        verifyNoMoreInteractions(defaultApi);
    }

    @Test
    public void testGetAllSimilarProducts_ApiException() throws ApiException {
        String productId = "123";

        when(defaultApi.getProductSimilarids(productId)).thenThrow(new ApiException("API error"));

        List<ProductDetail> result = productServiceImpl.getAllSimilarProducts(productId);
        assertEquals(Collections.emptyList(), result);

        verify(defaultApi, times(1)).getProductSimilarids(productId);
        verifyNoMoreInteractions(defaultApi);
    }
}