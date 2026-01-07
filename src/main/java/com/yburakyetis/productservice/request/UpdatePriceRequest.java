package com.yburakyetis.productservice.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UpdatePriceRequest(
    @NotNull(message = "Price cannot be null") @DecimalMin(value = "0.0", inclusive = false,
                                                           message = "Price must be greater than zero") BigDecimal price) {

}
