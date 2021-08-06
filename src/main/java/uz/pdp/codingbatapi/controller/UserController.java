package uz.pdp.codingbatapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbatapi.entity.User;
import uz.pdp.codingbatapi.payload.UserDto;
import uz.pdp.codingbatapi.payload.response.ApiResponse;
import uz.pdp.codingbatapi.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/api/users")
    public HttpEntity<List<User>> getListMethod(){
        List<User> users = userService.getMethod();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/api/users/{id}")
    public HttpEntity<User> getByIdMethod(@PathVariable Integer id){
        User user = userService.getByIdMethod(id);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/api/users")
    public HttpEntity<ApiResponse> addMethod(@Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.addMethod(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED :HttpStatus.CONFLICT).body(apiResponse);
    }
    @PutMapping("/api/users/{id}")
    public HttpEntity<ApiResponse> updateMethod(@Valid @RequestBody UserDto userDto,@PathVariable Integer id){
        ApiResponse apiResponse = userService.updateMethod(userDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }
    @DeleteMapping("/api/users/{id}")
    public HttpEntity<ApiResponse> deleteMethod(@PathVariable Integer id){
        ApiResponse apiResponse = userService.deleteMethod(id);
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
