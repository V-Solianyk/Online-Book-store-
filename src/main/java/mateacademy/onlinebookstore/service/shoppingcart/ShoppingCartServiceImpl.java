package mateacademy.onlinebookstore.service.shoppingcart;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.shoppingcart.CartItemResponseDto;
import mateacademy.onlinebookstore.dto.shoppingcart.CreateCartItemRequestDto;
import mateacademy.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import mateacademy.onlinebookstore.dto.shoppingcart.UpdateCartItemRequestDto;
import mateacademy.onlinebookstore.exception.EntityNotFoundException;
import mateacademy.onlinebookstore.mapper.ShoppingCartMapper;
import mateacademy.onlinebookstore.model.Book;
import mateacademy.onlinebookstore.model.CartItem;
import mateacademy.onlinebookstore.model.ShoppingCart;
import mateacademy.onlinebookstore.model.User;
import mateacademy.onlinebookstore.repository.book.BookRepository;
import mateacademy.onlinebookstore.repository.cartitem.CartItemRepository;
import mateacademy.onlinebookstore.repository.shopingcart.ShoppingCartRepository;
import mateacademy.onlinebookstore.repository.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper mapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartResponseDto getShoppingCartByUserId() {
        ShoppingCart shoppingCart = getShoppingCart(getUser().getId());
        return mapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto create(CreateCartItemRequestDto requestDto) {
        Long userId = getUser().getId();
        Book book = getBook(requestDto);
        CartItem cartItem = mapper.toModel(requestDto);
        cartItem.setBook(book);
        CartItem item = cartItemRepository.save(cartItem);
        ShoppingCart shoppingCart = getShoppingCart(userId);
        shoppingCart.getCartItems().add(item);

        return mapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    @Transactional
    public CartItemResponseDto update(UpdateCartItemRequestDto requestDto, Long cartItemId) {
        CartItem cartItem = getCartItem(cartItemId);
        cartItem.setQuantity(requestDto.getQuantity());
        return mapper.toCartItemDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void delete(Long cartItemId) {
        getCartItem(cartItemId);
        cartItemRepository.deleteById(cartItemId);
    }

    private CartItem getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find cartItem by id: " + cartItemId));
    }

    private ShoppingCart getShoppingCart(Long userId) {
        if (shoppingCartRepository.findByUserIdWithItems(userId).isPresent()) {
            return shoppingCartRepository.findByUserIdWithItems(userId).get();
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(getUser());
        return shoppingCartRepository.save(shoppingCart);
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername()).get();
    }

    private Book getBook(CreateCartItemRequestDto requestDto) {
        return bookRepository.findByIdWithCategories(requestDto.getBookId())
                .orElseThrow(() -> new NoSuchElementException("Book doesn't exist with this id: "
                        + requestDto.getBookId()));
    }
}
