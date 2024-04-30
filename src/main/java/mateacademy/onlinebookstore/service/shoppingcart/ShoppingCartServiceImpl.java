package mateacademy.onlinebookstore.service.shoppingcart;

import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.shoppingcart.CreateCartItemRequestDto;
import mateacademy.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import mateacademy.onlinebookstore.dto.shoppingcart.UpdateCartItemRequestDto;
import mateacademy.onlinebookstore.exception.EntityNotFoundException;
import mateacademy.onlinebookstore.mapper.ShoppingCartMapper;
import mateacademy.onlinebookstore.model.CartItem;
import mateacademy.onlinebookstore.model.ShoppingCart;
import mateacademy.onlinebookstore.repository.cartitem.CartItemRepository;
import mateacademy.onlinebookstore.repository.shopingcart.ShoppingCartRepository;
import mateacademy.onlinebookstore.repository.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper mapper;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    public ShoppingCartResponseDto getShoppingCartByUserId() {
        ShoppingCart shoppingCart = getShoppingCart(getUserId());
        return mapper.toDto(shoppingCart);
    }

    @Override
    public void create(CreateCartItemRequestDto requestDto) {
        Long userId = getUserId();
        CartItem item = cartItemRepository.save(mapper.toModel(requestDto));
        ShoppingCart shoppingCart = getShoppingCart(userId);

        shoppingCart.getCartItems().add(item);
    }

    @Override
    public void update(UpdateCartItemRequestDto requestDto, Long cartItemId) {
        CartItem cartItem = getCartItem(cartItemId);
        cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());
    }

    @Override
    public void delete(Long cartItemId) {
        CartItem cartItem = getCartItem(cartItemId);
        cartItem.setDeleted(true);
        cartItemRepository.save(cartItem);
    }

    private CartItem getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find cartItem by id: " + cartItemId));
    }

    private ShoppingCart getShoppingCart(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElse(shoppingCartRepository.save(new ShoppingCart()));
    }

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getDetails();
        return userRepository.findByEmail(email).get().getId();
    }
}
