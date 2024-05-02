package mateacademy.onlinebookstore.mapper;

import mateacademy.onlinebookstore.config.MapperConfig;
import mateacademy.onlinebookstore.dto.order.OrderItemsResponseDto;
import mateacademy.onlinebookstore.dto.order.OrderRequestDto;
import mateacademy.onlinebookstore.dto.order.OrderResponseDto;
import mateacademy.onlinebookstore.model.Order;
import mateacademy.onlinebookstore.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {
    OrderResponseDto orderToDto(Order order);

    OrderItemsResponseDto orderItemToDto(OrderItem orderItem);

    Order orderRequestDtoToModel(OrderRequestDto orderRequestDto);
}
