package uz.pdp.codingbatapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbatapi.entity.Category;
import uz.pdp.codingbatapi.entity.Language;
import uz.pdp.codingbatapi.payload.CategoryDto;
import uz.pdp.codingbatapi.payload.LanguageDto;
import uz.pdp.codingbatapi.payload.response.ApiResponse;
import uz.pdp.codingbatapi.repository.CategoryRepository;
import uz.pdp.codingbatapi.repository.LanguageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public List<Language> getMethod(){
        List<Language> languages = languageRepository.findAll();
        return languages;
    }
    public Language getByIdMethod(Integer id){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (!optionalLanguage.isPresent())
            return new Language();
        return optionalLanguage.get();
    }
    public ApiResponse addMethod(LanguageDto languageDto){
        boolean exists = languageRepository.existsByName(languageDto.getName());
        if (exists)
            return new ApiResponse("This Language is already exist",false);
        Language language=new Language();
        language.setName(languageDto.getName());
        List<Category> categories = categoryRepository.findAllById(languageDto.getCategoriesListId());
        language.setCategories(categories);
        languageRepository.save(language);
        return new ApiResponse("Language successfully added",true);
    }
    public ApiResponse updateMethod(LanguageDto languageDto,Integer id){
        boolean exists = languageRepository.existsByNameAndIdNot(languageDto.getName(), id);
        if (exists)
            return new ApiResponse("This Language is already exist",false);
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (!optionalLanguage.isPresent())
            return new ApiResponse("This Language doesn't exist", false);
        Language language = optionalLanguage.get();
        language.setName(languageDto.getName());
        List<Category> categories = categoryRepository.findAllById(languageDto.getCategoriesListId());
        language.setCategories(categories);
        languageRepository.save(language);
        return new ApiResponse("Language successfully updated",true);
    }
    public ApiResponse deleteMethod(Integer id){
        try {
            languageRepository.deleteById(id);
            return new ApiResponse("Language successfully deleted",true);
        } catch (Exception e){
            return new ApiResponse("Error in deleting",false);
        }
    }
}
