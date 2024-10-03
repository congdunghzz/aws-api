package ueh.congdunghzz.aws.service.user;

import ueh.congdunghzz.aws.model.response.UserResponse;

public interface UserService {
    UserResponse getById (String id);
}
