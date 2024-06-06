package mateacademy.onlinebookstore.mapper;

import mateacademy.onlinebookstore.config.MapperConfig;
import mateacademy.onlinebookstore.dto.shoppingcart.CartItemResponseDto;
import mateacademy.onlinebookstore.dto.shoppingcart.CreateCartItemRequestDto;
import mateacademy.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import mateacademy.onlinebookstore.dto.shoppingcart.UpdateCartItemRequestDto;
import mateacademy.onlinebookstore.model.CartItem;
import mateacademy.onlinebookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "id")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemResponseDto toCartItemDto(CartItem cartItem);

    CreateCartItemRequestDto toRequestDto(CartItem cartItem);

    CartItem toModel(CreateCartItemRequestDto requestDto);

    UpdateCartItemRequestDto toUpdateRequestDto(CartItem cartItem);
}
