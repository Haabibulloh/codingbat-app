package uz.pdp.codingbatapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDto {
    @NotNull(message = "Name doesn't have to be empty ")
    private String name;

    private List<Integer> categoriesListId;
}
