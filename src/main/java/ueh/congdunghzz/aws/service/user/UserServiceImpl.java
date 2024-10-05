package ueh.congdunghzz.aws.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ueh.congdunghzz.aws.common.Validator;
import ueh.congdunghzz.aws.enitity.User;
import ueh.congdunghzz.aws.model.response.UserResponse;
import ueh.congdunghzz.aws.repository.UserRepository;
import ueh.congdunghzz.aws.security.AuthUser;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public UserResponse getById(String id) {
        User user = userRepository.findById(id).orElse(null);
        Validator.notNull(user, HttpStatus.NOT_FOUND, "User Not Found");
        return new UserResponse(user);
    }

    @Override
    public UserResponse getByAuth(AuthUser authUser) {
        return getById(authUser.getId());
    }
}
