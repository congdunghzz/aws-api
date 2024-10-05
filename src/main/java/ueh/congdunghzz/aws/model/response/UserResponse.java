package ueh.congdunghzz.aws.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ueh.congdunghzz.aws.common.enums.UserRole;
import ueh.congdunghzz.aws.enitity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String name;
    private String username;
    private UserRole role;

    public UserResponse(User user){
        this.id = user.getId();
        this.name =user.getName();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
