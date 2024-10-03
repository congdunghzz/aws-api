package ueh.congdunghzz.aws.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest implements Serializable {

    @NotBlank(message = "username must not be null")
    private String username;
    @NotBlank(message = "password must not be null")
    private String password;
    @NotBlank(message = "name must not be null")
    private String name;
}
