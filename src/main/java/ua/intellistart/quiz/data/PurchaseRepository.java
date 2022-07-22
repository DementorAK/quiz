package ua.intellistart.quiz.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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