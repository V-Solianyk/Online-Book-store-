package mateacademy.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.order.OrderItemsResponseDto;
import mateacademy.onlinebookstore.dto.order.OrderRequestDto;
import mateacademy.onlinebookstore.dto.order.OrderResponseDto;
import mateacademy.onlinebookstore.dto.order.UpdateOrderStatusDto;
import mateacademy.onlinebookstore.service.order.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Create a new order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto create(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.create(orderRequestDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Retrieve user's order history")
    public Set<OrderResponseDto> getOrders(Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Update order status")
    public boolean updateOrderStatus(
            @PathVariable Long id, @RequestBody UpdateOrderStatusDto orderStatusDto) {
        return orderService.updateOrderStatus(orderStatusDto, id);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Retrieve all Order Items for a specific order")
    public Set<OrderItemsResponseDto> getOrderItemsForSpecificOrder(
            @PathVariable Long orderId, Pageable pageable) {
        return orderService.getAllOrderItemsByOrderId(orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(description = "Retrieve a specific Order Item within an order")
    public OrderItemsResponseDto getOrderItemByOrderIdAndItemId(
            @PathVariable Long orderId, @PathVariable Long itemId) {
        return orderService.getSpecificOrderItemByOrderAndItemIds(orderId, itemId);
    }
}
