package ueh.congdunghzz.aws.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ueh.congdunghzz.aws.common.Validator;
import ueh.congdunghzz.aws.common.enums.ActiveStatus;
import ueh.congdunghzz.aws.common.enums.UserRole;
import ueh.congdunghzz.aws.common.exception.ApplicationException;
import ueh.congdunghzz.aws.common.util.UniqueID;
import ueh.congdunghzz.aws.enitity.User;
import ueh.congdunghzz.aws.model.request.AuthRequest;
import ueh.congdunghzz.aws.model.request.RegisterRequest;
import ueh.congdunghzz.aws.model.response.AuthResponse;
import ueh.congdunghzz.aws.repository.UserRepository;
import ueh.congdunghzz.aws.security.CustomUserDetail;
import ueh.congdunghzz.aws.security.jwt.JwtProvider;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getUsername(),
                      request.getPassword()
              )
        );
        String token = jwtProvider.generateToken((CustomUserDetail)authentication.getPrincipal());
        return new AuthResponse(token, ((CustomUserDetail)authentication.getPrincipal()).getUser().getId());
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        User userInDb = userRepository.findByUsername(request.getUsername());
        Validator.mustNull(userInDb, HttpStatus.BAD_REQUEST, "Username is already existed");
        User user = User.builder()
                .id(UniqueID.getUUID())
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(ActiveStatus.ACTIVE)
                .role(UserRole.USER)
                .build();
        User savedUser = userRepository.insert(user);
        return new AuthResponse(jwtProvider.generateToken(new CustomUserDetail(savedUser)), savedUser.getId());
    }

}
