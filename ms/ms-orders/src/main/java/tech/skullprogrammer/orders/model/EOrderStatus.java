package tech.skullprogrammer.orders.model;

public enum EOrderStatus {
    NEW,
    PENDING,
    STOCK_RESERVED,
    CANCELED,
    NO_STOCK,
    PAID,
    PAYMENT_FAILED,
    COMPLETED,
}
