package ua.intellistart.quiz.data;

import org.springframework.data.repository.CrudRepository;
import ua.intellistart.quiz.model.Product;

public interface ProductsRepository extends CrudRepository<Product, Long> {}