package ueh.congdunghzz.aws.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ueh.congdunghzz.aws.common.util.ResponseUtil;
import ueh.congdunghzz.aws.security.AuthUser;
import ueh.congdunghzz.aws.service.user.UserService;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id){
        return ResponseUtil.successResponse(userService.getById(id));
    }
    @GetMapping("/auth")
    public ResponseEntity<?> getByAuth(AuthUser authUser){
        return ResponseUtil.successResponse(userService.getByAuth(authUser));
    }

}
