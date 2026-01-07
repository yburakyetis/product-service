package com.yburakyetis.productservice.service.impl;

import com.yburakyetis.productservice.config.CacheConfig;
import com.yburakyetis.productservice.request.AddProductRequest;
import com.yburakyetis.productservice.dto.ProductDTO;
import com.yburakyetis.productservice.entity.Product;
import com.yburakyetis.productservice.exception.ProductNotFoundException;
import com.yburakyetis.productservice.repository.ProductRepository;
import com.yburakyetis.productservice.service.ProductService;
import com.yburakyetis.productservice.request.UpdatePriceRequest;
import com.yburakyetis.productservice.request.UpdateStockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.yburakyetis.productservice.mapper.ProductMapper.INSTANCE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public ProductDTO create(AddProductRequest productRequest) {
    Product product = INSTANCE.toEntity(productRequest);
    Product savedProduct = productRepository.save(product);
    return INSTANCE.toDto(savedProduct);
  }

  @Override
  @Cacheable(value = CacheConfig.CACHE_NAME, key = "#id")
  public ProductDTO getById(Long id) {
    Product product = findById(id);
    return INSTANCE.toDto(product);
  }

  @Override
  @Transactional
  @CacheEvict(value = CacheConfig.CACHE_NAME, key = "#id")
  public ProductDTO updateStock(Long id, UpdateStockRequest stockRequest) {
    Product product = findById(id);
    product.setStock(stockRequest.stock());
    Product updatedProduct = productRepository.save(product);
    return INSTANCE.toDto(updatedProduct);
  }

  @Override
  @Transactional
  @CacheEvict(value = CacheConfig.CACHE_NAME, key = "#id")
  public ProductDTO updatePrice(Long id, UpdatePriceRequest priceRequest) {
    Product product = findById(id);
    product.setPrice(priceRequest.price());
    Product updatedProduct = productRepository.save(product);
    return INSTANCE.toDto(updatedProduct);
  }

  @Override
  public Page<ProductDTO> getAll(Pageable pageable) {
    return productRepository.findAll(pageable)
                            .map(INSTANCE::toDto);
  }

  private Product findById(Long id) {
    return productRepository.findById(id)
                            .orElseThrow(
                                () -> new ProductNotFoundException("Product not found with id: " + id));
  }
}
