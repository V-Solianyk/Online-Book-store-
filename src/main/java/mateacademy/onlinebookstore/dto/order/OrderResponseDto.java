package mateacademy.onlinebookstore.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import mateacademy.onlinebookstore.model.Order;

@Setter
@Getter
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemsResponseDto> orderItemsDto;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Order.Status status;
}
