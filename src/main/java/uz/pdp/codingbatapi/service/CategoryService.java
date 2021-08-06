package uz.pdp.codingbatapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbatapi.entity.Category;
import uz.pdp.codingbatapi.entity.Language;
import uz.pdp.codingbatapi.entity.User;
import uz.pdp.codingbatapi.payload.CategoryDto;
import uz.pdp.codingbatapi.payload.UserDto;
import uz.pdp.codingbatapi.payload.response.ApiResponse;
import uz.pdp.codingbatapi.repository.CategoryRepository;
import uz.pdp.codingbatapi.repository.LanguageRepository;
import uz.pdp.codingbatapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LanguageRepository languageRepository;

    public List<Category> getMethod() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public Category getByIdMethod(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new Category();
        return optionalCategory.get();
    }

    public ApiResponse addMethod(CategoryDto categoryDto) {
        boolean exists = categoryRepository.existsByName(categoryDto.getName());
        if (exists)
            return new ApiResponse("This Category is already exist", false);
        Optional<Language> optionalLanguage = languageRepository.findById(categoryDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return new ApiResponse("This Language doesn't exist",false);
        Language language = optionalLanguage.get();
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setLanguage(language);
        categoryRepository.save(category);
        return new ApiResponse("Category successfully added", true);
    }

    public ApiResponse updateMethod(CategoryDto categoryDto, Integer id) {
        boolean exists = categoryRepository.existsByNameAndIdNot(categoryDto.getName(), id);
        if (exists)
            return new ApiResponse("This Category is already exist", false);
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new ApiResponse("This Category doesn't exist", false);
        Optional<Language> optionalLanguage = languageRepository.findById(categoryDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return new ApiResponse("This Language doesn't exist",false);
        Language language = optionalLanguage.get();
        Category category = optionalCategory.get();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setLanguage(language);
        categoryRepository.save(category);
        return new ApiResponse("Category successfully updated", true);
    }

    public ApiResponse deleteMethod(Integer id) {
        try {
            categoryRepository.deleteById(id);
            return new ApiResponse("Category successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }
}
