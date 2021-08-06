package uz.pdp.codingbatapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbatapi.entity.Example;
import uz.pdp.codingbatapi.entity.User;
import uz.pdp.codingbatapi.payload.ExampleDto;
import uz.pdp.codingbatapi.payload.UserDto;
import uz.pdp.codingbatapi.payload.response.ApiResponse;
import uz.pdp.codingbatapi.service.ExampleService;
import uz.pdp.codingbatapi.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class ExampleController {
    @Autowired
    ExampleService exampleService;

    @GetMapping("/api/example")
    public HttpEntity<List<Example>> getListMethod(){
        List<Example> examples = exampleService.getMethod();
        return ResponseEntity.ok(examples);
    }
    @GetMapping("/api/example/{id}")
    public HttpEntity<Example> getByIdMethod(@PathVariable Integer id){
        Example example = exampleService.getByIdMethod(id);
        return ResponseEntity.ok(example);
    }
    @PostMapping("/api/example")
    public HttpEntity<ApiResponse> addMethod(@Valid @RequestBody ExampleDto exampleDto){
        ApiResponse apiResponse = exampleService.addMethod(exampleDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED :HttpStatus.CONFLICT).body(apiResponse);
    }
    @PutMapping("/api/example/{id}")
    public HttpEntity<ApiResponse> updateMethod(@Valid @RequestBody ExampleDto exampleDto,@PathVariable Integer id){
        ApiResponse apiResponse = exampleService.updateMethod(exampleDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }
    @DeleteMapping("/api/example/{id}")
    public HttpEntity<ApiResponse> deleteMethod(@PathVariable Integer id){
        ApiResponse apiResponse = exampleService.deleteMethod(id);
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
