package ueh.congdunghzz.aws.controler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ueh.congdunghzz.aws.common.util.ResponseUtil;
import ueh.congdunghzz.aws.model.request.AuthRequest;
import ueh.congdunghzz.aws.model.request.RegisterRequest;
import ueh.congdunghzz.aws.service.AuthenticationService.AuthenticationService;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request){
        return ResponseUtil.successResponse(authenticationService.login(request));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request){
        return ResponseUtil.successResponse(authenticationService.register(request));
    }
}
