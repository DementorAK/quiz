package ua.intellistart.quiz.data;

import org.springframework.data.repository.CrudRepository;
import ua.intellistart.quiz.model.Product;
import ua.intellistart.quiz.model.Purchase;
import ua.intellistart.quiz.model.User;

import java.util.List;
import java.util.UUID;

public interface PurchaseRepository extends CrudRepository<Purchase, UUID> {
    List<Purchase> findByUser(User user);
    List<Purchase> findByProduct(Product product);
    long removeByUser(User user);
    long removeByProduct(Product product);
}