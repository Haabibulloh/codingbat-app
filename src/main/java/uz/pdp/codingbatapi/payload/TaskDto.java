package uz.pdp.codingbatapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.codingbatapi.entity.Language;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @NotNull(message = "Name doesn't have to be empty ")
    private String name;
    @NotNull(message = "Text doesn't have to be empty ")
    private String text;

    private String solution;

    private String hint;

    private String method;

    private String hasStar;

    private Integer categoryId;
}
