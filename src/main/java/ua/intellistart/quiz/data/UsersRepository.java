package ua.intellistart.quiz.data;

import org.springframework.data.repository.CrudRepository;
import ua.intellistart.quiz.model.User;

public interface UsersRepository extends CrudRepository<User, Long> {}