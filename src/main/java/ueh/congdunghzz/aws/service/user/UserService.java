package ueh.congdunghzz.aws.service.user;

import ueh.congdunghzz.aws.model.response.UserResponse;
import ueh.congdunghzz.aws.security.AuthUser;

public interface UserService {
    UserResponse getById (String id);
    UserResponse getByAuth(AuthUser authUser);
}
