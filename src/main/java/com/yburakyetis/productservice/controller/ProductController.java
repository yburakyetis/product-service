package com.yburakyetis.productservice.controller;

import com.yburakyetis.productservice.request.AddProductRequest;
import com.yburakyetis.productservice.request.UpdatePriceRequest;
import com.yburakyetis.productservice.request.UpdateStockRequest;
import com.yburakyetis.productservice.dto.ProductDTO;
import com.yburakyetis.productservice.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductDTO> create(@Valid @RequestBody AddProductRequest productRequest) {
    ProductDTO productDTO = productService.create(productRequest);
    return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> getById(@PathVariable @Positive Long id) {
    ProductDTO productDTO = productService.getById(id);
    return ResponseEntity.ok(productDTO);
  }

  @PatchMapping("/{id}/stock")
  public ResponseEntity<ProductDTO> updateStock(@PathVariable @Positive Long id,
                                                @Valid @RequestBody UpdateStockRequest stockRequest) {
    ProductDTO productDTO = productService.updateStock(id, stockRequest);
    return ResponseEntity.ok(productDTO);
  }

  @PatchMapping("/{id}/price")
  public ResponseEntity<ProductDTO> updatePrice(@PathVariable @Positive Long id,
                                                @Valid @RequestBody UpdatePriceRequest priceRequest) {
    ProductDTO productDTO = productService.updatePrice(id, priceRequest);
    return ResponseEntity.ok(productDTO);
  }

  @GetMapping
  public ResponseEntity<Page<ProductDTO>> getAll(@PageableDefault(size = 20) Pageable pageable) {
    Page<ProductDTO> productDTOS = productService.getAll(pageable);
    return ResponseEntity.ok(productDTOS);
  }
}
