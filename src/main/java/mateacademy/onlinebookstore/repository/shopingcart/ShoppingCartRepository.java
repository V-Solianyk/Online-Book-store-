package mateacademy.onlinebookstore.repository.shopingcart;

import java.util.Optional;
import mateacademy.onlinebookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserId(Long userId);
}
