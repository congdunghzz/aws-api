package ueh.congdunghzz.aws.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ueh.congdunghzz.aws.enitity.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
