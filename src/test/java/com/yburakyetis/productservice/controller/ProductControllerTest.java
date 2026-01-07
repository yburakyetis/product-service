package com.yburakyetis.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yburakyetis.productservice.request.AddProductRequest;
import com.yburakyetis.productservice.dto.ProductDTO;
import com.yburakyetis.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldReturnCreatedWhenProductIsCreated() throws Exception {
    var addProductRequest = AddProductRequest.builder()
                                             .name("Test Product")
                                             .price(BigDecimal.valueOf(100.0))
                                             .stock(10)
                                             .build();

    var productDTO = ProductDTO.builder()
                               .id(1L)
                               .name("Test Product")
                               .price(BigDecimal.valueOf(100.0))
                               .stock(10)
                               .build();

    when(productService.create(any(AddProductRequest.class))).thenReturn(productDTO);

    mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addProductRequest)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.id").value(1L))
           .andExpect(jsonPath("$.name").value("Test Product"));
  }

  @Test
  void shouldReturnProductWhenGetById() throws Exception {
    var productDTO = ProductDTO.builder()
                               .id(1L)
                               .name("Test Product")
                               .build();

    when(productService.getById(1L)).thenReturn(productDTO);

    mockMvc.perform(get("/api/products/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1L))
           .andExpect(jsonPath("$.name").value("Test Product"));
  }
}
