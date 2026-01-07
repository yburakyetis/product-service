package com.yburakyetis.productservice.service;

import com.yburakyetis.productservice.request.AddProductRequest;
import com.yburakyetis.productservice.dto.ProductDTO;

import com.yburakyetis.productservice.request.UpdatePriceRequest;
import com.yburakyetis.productservice.request.UpdateStockRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  ProductDTO create(AddProductRequest productRequest);

  ProductDTO getById(Long id);

  ProductDTO updateStock(Long id, UpdateStockRequest stockRequest);

  ProductDTO updatePrice(Long id, UpdatePriceRequest priceRequest);

  Page<ProductDTO> getAll(Pageable pageable);
}
