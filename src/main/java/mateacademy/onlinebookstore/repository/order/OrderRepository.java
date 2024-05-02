package mateacademy.onlinebookstore.repository.order;

import java.util.List;
import mateacademy.onlinebookstore.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long id, Pageable pageable);
}
