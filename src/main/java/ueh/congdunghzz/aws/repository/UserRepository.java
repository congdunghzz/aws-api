package ueh.congdunghzz.aws.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ueh.congdunghzz.aws.enitity.User;

import java.util.Optional;

public interface UserRepository {
    User findByUsername(String username);

    User insert(User user);

    Optional<User> findById(String id);
}
