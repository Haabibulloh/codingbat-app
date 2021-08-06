package uz.pdp.codingbatapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExampleDto {
    @NotNull(message = "Email doesn't have to be empty ")
    private String text;

    private Integer taskId;
}
