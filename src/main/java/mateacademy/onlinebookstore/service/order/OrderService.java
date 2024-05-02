package mateacademy.onlinebookstore.service.order;

import java.util.Set;
import mateacademy.onlinebookstore.dto.order.OrderItemsResponseDto;
import mateacademy.onlinebookstore.dto.order.OrderRequestDto;
import mateacademy.onlinebookstore.dto.order.OrderResponseDto;
import mateacademy.onlinebookstore.dto.order.UpdateOrderStatusDto;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto create(OrderRequestDto orderRequestDto);

    Set<OrderResponseDto> getAll(Pageable pageable);

    boolean updateOrderStatus(UpdateOrderStatusDto orderStatusDto, Long id);

    Set<OrderItemsResponseDto> getAllOrderItemsByOrderId(Long id, Pageable pageable);

    OrderItemsResponseDto getSpecificOrderItemByOrderAndItemIds(Long orderId, Long itemId);
}
