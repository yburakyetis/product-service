package com.yburakyetis.productservice.mapper;

import com.yburakyetis.productservice.request.AddProductRequest;
import com.yburakyetis.productservice.dto.ProductDTO;
import com.yburakyetis.productservice.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

  private ProductMapper productMapper;

  @BeforeEach
  void setUp() {
    productMapper = Mappers.getMapper(ProductMapper.class);
  }

  @Test
  void shouldMapToEntityCorrectly() {
    var addProductRequest = AddProductRequest.builder()
                                             .name("Test Product")
                                             .description("Description")
                                             .price(BigDecimal.valueOf(100.0))
                                             .stock(10)
                                             .build();

    Product product = productMapper.toEntity(addProductRequest);

    assertNotNull(product);
    assertEquals(addProductRequest.name(), product.getName());
    assertEquals(addProductRequest.description(), product.getDescription());
    assertEquals(addProductRequest.price(), product.getPrice());
    assertEquals(addProductRequest.stock(), product.getStock());
  }

  @Test
  void shouldMapToDtoCorrectly() {
    var product = Product.builder()
                         .id(1L)
                         .name("Test Product")
                         .description("Description")
                         .price(BigDecimal.valueOf(100.0))
                         .stock(10)
                         .build();

    ProductDTO productDTO = productMapper.toDto(product);

    assertNotNull(productDTO);
    assertEquals(product.getId(), productDTO.id());
    assertEquals(product.getName(), productDTO.name());
    assertEquals(product.getDescription(), productDTO.description());
    assertEquals(product.getPrice(), productDTO.price());
    assertEquals(product.getStock(), productDTO.stock());
  }

  @Test
  void shouldReturnNullWhenToEntityAndDtoIsNull() {
    assertNull(productMapper.toEntity(null));
  }

  @Test
  void shouldReturnNullWhenToDtoAndEntityIsNull() {
    assertNull(productMapper.toDto(null));
  }
}
