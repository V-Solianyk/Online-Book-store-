package mateacademy.onlinebookstore.repository.order;

import java.util.Optional;
import mateacademy.onlinebookstore.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Page<OrderItem> findAllByOrderId(Long orderId, Pageable pageable);

    Optional<OrderItem> findByOrderIdAndBookId(Long orderId, Long bookId);
}
