package com.yburakyetis.productservice.service.impl;

import com.yburakyetis.productservice.request.AddProductRequest;
import com.yburakyetis.productservice.dto.ProductDTO;
import com.yburakyetis.productservice.entity.Product;
import com.yburakyetis.productservice.exception.ProductNotFoundException;

import com.yburakyetis.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductServiceImpl productService;

  @Test
  void shouldSaveAndReturnProductWhenCreateProduct() {
    var addProductRequest = AddProductRequest.builder()
                                             .name("Test Product")
                                             .price(BigDecimal.TEN)
                                             .build();

    when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
      Product p = invocation.getArgument(0);
      p.setId(1L);
      return p;
    });

    ProductDTO productDTO = productService.create(addProductRequest);

    assertNotNull(productDTO);
    assertEquals(1L, productDTO.id());
    assertEquals(addProductRequest.name(), productDTO.name());
    verify(productRepository).save(any(Product.class));
  }

  @Test
  void shouldReturnProductWhenGetByIdExists() {
    Long id = 1L;
    var product = Product.builder().id(id).name("Test Product").build();

    when(productRepository.findById(id)).thenReturn(Optional.of(product));

    ProductDTO productDTO = productService.getById(id);

    assertNotNull(productDTO);
    assertEquals(id, productDTO.id());
  }

  @Test
  void shouldThrowExceptionWhenGetByIdNotFound() {
    Long id = 1L;
    when(productRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> productService.getById(id));
  }
}
