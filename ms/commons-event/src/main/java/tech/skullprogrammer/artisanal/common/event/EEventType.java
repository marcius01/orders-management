package tech.skullprogrammer.artisanal.common.event;

public enum EEventType {
    STOCK_ADJUSTMENT_FAILED_EVENT ("StockAdjustmentFailedEvent"),
    STOCK_ADJUSTMENT_SUCCESS_EVENT ("StockAdjustmentSuccessEvent"),
    ORDER_INITIATED_EVENT ("OrderInitiatedEvent"),;

    private String value;
    EEventType(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
