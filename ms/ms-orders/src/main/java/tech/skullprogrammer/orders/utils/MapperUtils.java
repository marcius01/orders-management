package tech.skullprogrammer.orders.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import tech.skullprogrammer.artisanal.common.event.OrderInitiatedEvent;
import tech.skullprogrammer.artisanal.common.event.OrderStockReservationFailedEvent;
import tech.skullprogrammer.orders.model.Item;
import tech.skullprogrammer.orders.model.Order;
import tech.skullprogrammer.orders.model.dto.OrderResponseDTO;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface MapperUtils {

    @Mapping(source = "priceAtPurchaseSnapshot", target = "priceAtRequest")
    OrderInitiatedEvent.Item toEventItem(Item item);
    @Mapping(source = "quantity", target = "requestedQuantity")
    OrderStockReservationFailedEvent.FailedItem toFailedEventItem(Item item);
    List<OrderInitiatedEvent.Item> toEventItems(List<Item> items);
    List<OrderStockReservationFailedEvent.FailedItem> toFailEventItems(List<Item> items);
    OrderResponseDTO toOrderResponse(Order order);

}
