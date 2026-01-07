package com.yburakyetis.productservice.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AddProductRequest(
    @NotBlank(message = "Name is required") String name,
    String description,
    @NotNull(message = "Price is required") @DecimalMin(value = "0.0", inclusive = false,
                                                        message = "Price must be positive") BigDecimal price,
    @PositiveOrZero(message = "Stock cannot be negative") Integer stock) {

}
