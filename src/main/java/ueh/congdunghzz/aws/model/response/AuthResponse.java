package ueh.congdunghzz.aws.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class AuthResponse {
    private String token;
    private String userId;
}
