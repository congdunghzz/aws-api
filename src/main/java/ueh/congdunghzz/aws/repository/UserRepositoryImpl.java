package ueh.congdunghzz.aws.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ueh.congdunghzz.aws.enitity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{
    private static final List<User> users = new ArrayList<>();

    @Override
    public User insert (User user){
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return users
                .stream()
                .filter(user1 -> user1.getId().equals(id))
                .findFirst();
    }


    @Override
    public User findByUsername(String username) {
        return users.stream()
                .collect(Collectors.toMap(User::getUsername, Function.identity()))
                .getOrDefault(username, null);
    }
}
