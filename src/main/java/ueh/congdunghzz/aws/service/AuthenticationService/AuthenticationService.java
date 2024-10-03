package ueh.congdunghzz.aws.service.AuthenticationService;

import ueh.congdunghzz.aws.model.request.AuthRequest;
import ueh.congdunghzz.aws.model.request.RegisterRequest;
import ueh.congdunghzz.aws.model.response.AuthResponse;

public interface AuthenticationService {
    AuthResponse login(AuthRequest request);
    AuthResponse register(RegisterRequest request);
}
