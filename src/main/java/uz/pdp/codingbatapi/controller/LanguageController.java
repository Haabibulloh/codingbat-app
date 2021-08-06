package uz.pdp.codingbatapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbatapi.entity.Category;
import uz.pdp.codingbatapi.entity.Language;
import uz.pdp.codingbatapi.payload.CategoryDto;
import uz.pdp.codingbatapi.payload.LanguageDto;
import uz.pdp.codingbatapi.payload.response.ApiResponse;
import uz.pdp.codingbatapi.service.CategoryService;
import uz.pdp.codingbatapi.service.LanguageService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class LanguageController {
    @Autowired
    LanguageService languageService;

    @GetMapping("/api/language")
    public HttpEntity<List<Language>> getListMethod(){
        List<Language> languages = languageService.getMethod();
        return ResponseEntity.ok(languages);
    }
    @GetMapping("/api/language/{id}")
    public HttpEntity<Language> getByIdMethod(@PathVariable Integer id){
        Language language = languageService.getByIdMethod(id);
        return ResponseEntity.ok(language);
    }
    @PostMapping("/api/language")
    public HttpEntity<ApiResponse> addMethod(@Valid @RequestBody LanguageDto languageDto){
        ApiResponse apiResponse = languageService.addMethod(languageDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED :HttpStatus.CONFLICT).body(apiResponse);
    }
    @PutMapping("/api/language/{id}")
    public HttpEntity<ApiResponse> updateMethod(@Valid @RequestBody LanguageDto languageDto,@PathVariable Integer id){
        ApiResponse apiResponse = languageService.updateMethod(languageDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }
    @DeleteMapping("/api/language/{id}")
    public HttpEntity<ApiResponse> deleteMethod(@PathVariable Integer id){
        ApiResponse apiResponse = languageService.deleteMethod(id);
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
