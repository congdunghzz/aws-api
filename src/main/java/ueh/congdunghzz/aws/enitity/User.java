package ueh.congdunghzz.aws.enitity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ueh.congdunghzz.aws.common.enums.ActiveStatus;
import ueh.congdunghzz.aws.common.enums.UserRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User {
    @Id
    private String id;
    private String name;
    private String username;
    private String password;
    private UserRole role;
    private ActiveStatus status;
}
