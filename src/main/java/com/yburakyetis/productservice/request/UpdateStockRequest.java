package com.yburakyetis.productservice.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record UpdateStockRequest(
    @NotNull(message = "Stock cannot be null") @PositiveOrZero(message = "Stock cannot be negative") Integer stock) {

}
