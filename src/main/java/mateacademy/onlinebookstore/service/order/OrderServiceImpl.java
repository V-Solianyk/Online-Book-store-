package mateacademy.onlinebookstore.service.order;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mateacademy.onlinebookstore.dto.order.OrderItemsResponseDto;
import mateacademy.onlinebookstore.dto.order.OrderRequestDto;
import mateacademy.onlinebookstore.dto.order.OrderResponseDto;
import mateacademy.onlinebookstore.dto.order.UpdateOrderStatusDto;
import mateacademy.onlinebookstore.exception.EntityNotFoundException;
import mateacademy.onlinebookstore.mapper.OrderMapper;
import mateacademy.onlinebookstore.model.Order;
import mateacademy.onlinebookstore.model.OrderItem;
import mateacademy.onlinebookstore.model.ShoppingCart;
import mateacademy.onlinebookstore.model.User;
import mateacademy.onlinebookstore.repository.order.OrderItemRepository;
import mateacademy.onlinebookstore.repository.order.OrderRepository;
import mateacademy.onlinebookstore.repository.shopingcart.ShoppingCartRepository;
import mateacademy.onlinebookstore.repository.user.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    @Transactional
    public OrderResponseDto create(OrderRequestDto orderRequestDto) {
        Order order = orderMapper.orderRequestDtoToModel(orderRequestDto);
        order.setUser(getUser());
        order.setStatus(Order.Status.PENDING);
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserIdWithItems(getUser().getId()).get();
        BigDecimal total = shoppingCart.getCartItems().stream()
                .map(cartItem -> (BigDecimal.valueOf(cartItem.getQuantity())
                        .multiply(cartItem.getBook().getPrice())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(cartItem -> new OrderItem(cartItem.getBook(), cartItem.getQuantity(),
                        cartItem.getBook().getPrice(), cartItem.isDeleted()))
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);
        return orderMapper.orderToDto(orderRepository.save(order));
    }

    @Override
    public Set<OrderResponseDto> getAll(Pageable pageable) {
        Long userId = getUser().getId();
        return orderRepository.findAllByUserId(userId, pageable).stream()
                .map(orderMapper::orderToDto)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public boolean updateOrderStatus(UpdateOrderStatusDto orderStatusDto, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("The order by this id doesn't exist: " + id));
        if (orderStatusDto.getStatus().equals(Order.Status.COMPLETED)
                || orderStatusDto.getStatus().equals(Order.Status.PENDING)
                || orderStatusDto.getStatus().equals(Order.Status.DELIVERED)) {
            order.setStatus(orderStatusDto.getStatus());
            orderRepository.save(order);
            return true;
        }
        throw new RuntimeException("Can't update the order status by order id: " + id);
    }

    @Override
    public Set<OrderItemsResponseDto> getAllOrderItemsByOrderId(Long id, Pageable pageable) {
        return orderItemRepository.findAllByOrderId(id, pageable).stream()
                .map(orderMapper::orderItemToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderItemsResponseDto getSpecificOrderItemByOrderAndItemIds(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findByOrderIdAndBookId(orderId, itemId)
                .orElseThrow(() -> new EntityNotFoundException("The specific order item by"
                        + " order id - " + orderId + " and item id - " + itemId + " can't find"));
        return orderMapper.orderItemToDto(orderItem);
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getDetails();
        return userRepository.findByEmail(email).get();
    }
}
