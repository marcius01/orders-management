package tech.skullprogrammer.products.model.dto;

import jakarta.validation.constraints.NotNull;

public record StockAdjustmentRequest(@NotNull Integer quantity) {
}
