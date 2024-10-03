package ueh.congdunghzz.aws.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ueh.congdunghzz.aws.common.Validator;
import ueh.congdunghzz.aws.enitity.User;
import ueh.congdunghzz.aws.repository.UserRepository;
import ueh.congdunghzz.aws.security.CustomUserDetail;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        Validator.notNull(user, HttpStatus.NOT_FOUND, "Username is incorrect");
        return new CustomUserDetail(user);
    }
}
