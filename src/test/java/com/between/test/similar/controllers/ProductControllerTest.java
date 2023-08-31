package com.between.test.similar.controllers;


import com.between.test.similar.apiclient.client.ApiException;
import com.between.test.similar.apiclient.client.model.ProductDetail;
import com.between.test.similar.services.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {


    @Mock
    private ProductServiceImpl productServiceImpl;

    @InjectMocks
    private ProductController productController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductsSimilar_Success() throws ApiException {
        String productId = "123";

        List<ProductDetail> similarProducts = new ArrayList<>();
        ProductDetail productDetail1 = new ProductDetail();
        productDetail1.setId("456");
        productDetail1.setName("Product B");
        similarProducts.add(productDetail1);

        when(productServiceImpl.getAllSimilarProducts(productId)).thenReturn(similarProducts);

        ResponseEntity<List<ProductDetail>> responseEntity = productController.getProductsSimilar(productId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(similarProducts, responseEntity.getBody());

        verify(productServiceImpl, times(1)).getAllSimilarProducts(productId);
    }

    @Test
    public void testGetProductsSimilar_NoSimilarProducts() throws ApiException {
        String productId = "123";

        when(productServiceImpl.getAllSimilarProducts(productId)).thenReturn(new ArrayList<>());

        ResponseEntity<List<ProductDetail>> responseEntity = productController.getProductsSimilar(productId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(productServiceImpl, times(1)).getAllSimilarProducts(productId);
    }

    @Test
    public void testGetProductsSimilar_ApiException() throws ApiException {
        String productId = "123";

        when(productServiceImpl.getAllSimilarProducts(productId)).thenThrow(new ApiException("API error"));

        ResponseEntity<List<ProductDetail>> responseEntity = productController.getProductsSimilar(productId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        verify(productServiceImpl, times(1)).getAllSimilarProducts(productId);
    }
}