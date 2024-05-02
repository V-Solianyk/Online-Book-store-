package mateacademy.onlinebookstore.dto.order;

import lombok.Getter;
import lombok.Setter;
import mateacademy.onlinebookstore.model.Order;

@Getter
@Setter
public class UpdateOrderStatusDto {
    private Order.Status status;
}
