package mateacademy.onlinebookstore.controller;

import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.shoppingcart.CreateCartItemRequestDto;
import mateacademy.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import mateacademy.onlinebookstore.dto.shoppingcart.UpdateCartItemRequestDto;
import mateacademy.onlinebookstore.service.shoppingcart.ShoppingCartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ShoppingCartResponseDto get() {
        return shoppingCartService.getShoppingCartByUserId();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public void create(@RequestBody CreateCartItemRequestDto requestDto) {
        shoppingCartService.create(requestDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void update(
            @RequestBody UpdateCartItemRequestDto requestDto, @PathVariable Long cartItemId) {
        shoppingCartService.update(requestDto, cartItemId);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteByCartItemId(@PathVariable Long cartItemId) {
        shoppingCartService.delete(cartItemId);
    }
}
