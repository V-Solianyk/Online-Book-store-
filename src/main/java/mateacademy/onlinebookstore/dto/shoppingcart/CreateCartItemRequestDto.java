package mateacademy.onlinebookstore.dto.shoppingcart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCartItemRequestDto {
    private Long bookId;
    private int quantity;
}
