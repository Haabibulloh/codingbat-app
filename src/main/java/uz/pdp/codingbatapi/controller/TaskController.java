package uz.pdp.codingbatapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbatapi.entity.Task;
import uz.pdp.codingbatapi.entity.User;
import uz.pdp.codingbatapi.payload.TaskDto;
import uz.pdp.codingbatapi.payload.UserDto;
import uz.pdp.codingbatapi.payload.response.ApiResponse;
import uz.pdp.codingbatapi.service.TaskService;
import uz.pdp.codingbatapi.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping("/api/task")
    public HttpEntity<List<Task>> getListMethod(){
        List<Task> tasks = taskService.getMethod();
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("/api/task/{id}")
    public HttpEntity<Task> getByIdMethod(@PathVariable Integer id){
        Task task = taskService.getByIdMethod(id);
        return ResponseEntity.ok(task);
    }
    @PostMapping("/api/task")
    public HttpEntity<ApiResponse> addMethod(@Valid @RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.addMethod(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED :HttpStatus.CONFLICT).body(apiResponse);
    }
    @PutMapping("/api/task/{id}")
    public HttpEntity<ApiResponse> updateMethod(@Valid @RequestBody TaskDto taskDto,@PathVariable Integer id){
        ApiResponse apiResponse = taskService.updateMethod(taskDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }
    @DeleteMapping("/api/task/{id}")
    public HttpEntity<ApiResponse> deleteMethod(@PathVariable Integer id){
        ApiResponse apiResponse = taskService.deleteMethod(id);
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
