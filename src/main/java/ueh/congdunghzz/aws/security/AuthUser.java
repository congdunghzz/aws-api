package ueh.congdunghzz.aws.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ueh.congdunghzz.aws.common.enums.UserRole;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthUser {
    private String id;
    private String name;
    private UserRole role;
}
