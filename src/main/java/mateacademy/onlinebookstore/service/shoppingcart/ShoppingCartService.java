package mateacademy.onlinebookstore.service.shoppingcart;

import mateacademy.onlinebookstore.dto.shoppingcart.CartItemResponseDto;
import mateacademy.onlinebookstore.dto.shoppingcart.CreateCartItemRequestDto;
import mateacademy.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import mateacademy.onlinebookstore.dto.shoppingcart.UpdateCartItemRequestDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCartByUserId();

    ShoppingCartResponseDto create(CreateCartItemRequestDto requestDto);

    CartItemResponseDto update(UpdateCartItemRequestDto requestDto, Long cartItemId);

    void delete(Long cartItemId);
}
