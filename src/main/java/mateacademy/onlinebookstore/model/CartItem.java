package mateacademy.onlinebookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "cart_items")
@Data
@SQLDelete(sql = "UPDATE cart_items SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private int quantity;
    @Column(nullable = false)
    private boolean isDeleted = false;
}
