package uz.pdp.codingbatapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull(message = "Email doesn't have to be empty ")
    private String email;
    @NotNull(message = "Password doesn't have to be empty ")
    private String password;

}
