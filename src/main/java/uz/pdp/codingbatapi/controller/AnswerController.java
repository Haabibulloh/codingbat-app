package uz.pdp.codingbatapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbatapi.entity.Answer;
import uz.pdp.codingbatapi.entity.User;
import uz.pdp.codingbatapi.payload.AnswerDto;
import uz.pdp.codingbatapi.payload.UserDto;
import uz.pdp.codingbatapi.payload.response.ApiResponse;
import uz.pdp.codingbatapi.service.AnswerService;
import uz.pdp.codingbatapi.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @GetMapping("/api/answer")
    public HttpEntity<List<Answer>> getListMethod(){
        List<Answer> answers = answerService.getMethod();
        return ResponseEntity.ok(answers);
    }
    @GetMapping("/api/answer/{id}")
    public HttpEntity<Answer> getByIdMethod(@PathVariable Integer id){
        Answer answer = answerService.getByIdMethod(id);
        return ResponseEntity.ok(answer);
    }
    @PostMapping("/api/answer")
    public HttpEntity<ApiResponse> addMethod(@Valid @RequestBody AnswerDto answerDto){
        ApiResponse apiResponse = answerService.addMethod(answerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED :HttpStatus.CONFLICT).body(apiResponse);
    }
    @PutMapping("/api/answer/{id}")
    public HttpEntity<ApiResponse> updateMethod(@Valid @RequestBody AnswerDto answerDto, @PathVariable Integer id){
        ApiResponse apiResponse = answerService.updateMethod(answerDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }
    @DeleteMapping("/api/answer/{id}")
    public HttpEntity<ApiResponse> deleteMethod(@PathVariable Integer id){
        ApiResponse apiResponse = answerService.deleteMethod(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
