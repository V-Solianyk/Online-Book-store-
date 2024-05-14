package mateacademy.onlinebookstore.repository.shopingcart;

import java.util.Optional;
import mateacademy.onlinebookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc "
            + "JOIN FETCH sc.user u "
            + "JOIN FETCH sc.cartItems ci "
            + "WHERE u.id = :userId")
    Optional<ShoppingCart> findByUserIdWithItems(@Param("userId") Long userId);
}
