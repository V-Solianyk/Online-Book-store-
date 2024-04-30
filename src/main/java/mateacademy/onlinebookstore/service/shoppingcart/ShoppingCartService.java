package mateacademy.onlinebookstore.service.shoppingcart;

import mateacademy.onlinebookstore.dto.shoppingcart.CreateCartItemRequestDto;
import mateacademy.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import mateacademy.onlinebookstore.dto.shoppingcart.UpdateCartItemRequestDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCartByUserId();

    void create(CreateCartItemRequestDto requestDto);

    void update(UpdateCartItemRequestDto requestDto, Long cartItemId);

    void delete(Long cartItemId);
}
