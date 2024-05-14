package mateacademy.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.shoppingcart.CartItemResponseDto;
import mateacademy.onlinebookstore.dto.shoppingcart.CreateCartItemRequestDto;
import mateacademy.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import mateacademy.onlinebookstore.dto.shoppingcart.UpdateCartItemRequestDto;
import mateacademy.onlinebookstore.service.shoppingcart.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Get an user shopping cart")
    public ShoppingCartResponseDto get() {
        return shoppingCartService.getShoppingCartByUserId();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Create a new cart item in a shopping cart")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartResponseDto create(@RequestBody CreateCartItemRequestDto requestDto) {
        return shoppingCartService.create(requestDto);
    }

    @PutMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Update a quantity cart items in a shopping cart")
    public CartItemResponseDto update(
            @RequestBody UpdateCartItemRequestDto requestDto, @PathVariable Long cartItemId) {
        return shoppingCartService.update(requestDto, cartItemId);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Delete a cart items in a shopping cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCartItemId(@PathVariable Long cartItemId) {
        shoppingCartService.delete(cartItemId);
    }
}
