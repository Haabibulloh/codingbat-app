package uz.pdp.codingbatapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    @NotNull(message = "Name doesn't have to be empty ")
    private String text;

    private String isCorrect;

    private Integer taskId;

    private Integer userId;

}
