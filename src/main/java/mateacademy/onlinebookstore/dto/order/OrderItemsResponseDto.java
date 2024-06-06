package mateacademy.onlinebookstore.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemsResponseDto {
    private Long id;
    private Long bookId;
    private int quantity;
}
