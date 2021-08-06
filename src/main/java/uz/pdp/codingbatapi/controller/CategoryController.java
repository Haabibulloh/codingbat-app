package uz.pdp.codingbatapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbatapi.entity.Category;
import uz.pdp.codingbatapi.payload.CategoryDto;
import uz.pdp.codingbatapi.payload.response.ApiResponse;
import uz.pdp.codingbatapi.service.CategoryService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/api/category")
    public HttpEntity<List<Category>> getListMethod(){
        List<Category> categories = categoryService.getMethod();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/api/category/{id}")
    public HttpEntity<Category> getByIdMethod(@PathVariable Integer id){
        Category category = categoryService.getByIdMethod(id);
        return ResponseEntity.ok(category);
    }
    @PostMapping("/api/category")
    public HttpEntity<ApiResponse> addMethod(@Valid @RequestBody CategoryDto categoryDto){
        ApiResponse apiResponse = categoryService.addMethod(categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED :HttpStatus.CONFLICT).body(apiResponse);
    }
    @PutMapping("/api/category/{id}")
    public HttpEntity<ApiResponse> updateMethod(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer id){
        ApiResponse apiResponse = categoryService.updateMethod(categoryDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }
    @DeleteMapping("/api/category/{id}")
    public HttpEntity<ApiResponse> deleteMethod(@PathVariable Integer id){
        ApiResponse apiResponse = categoryService.deleteMethod(id);
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
