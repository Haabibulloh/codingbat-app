package uz.pdp.codingbatapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbatapi.entity.Category;
import uz.pdp.codingbatapi.entity.Language;
import uz.pdp.codingbatapi.entity.Task;
import uz.pdp.codingbatapi.payload.LanguageDto;
import uz.pdp.codingbatapi.payload.TaskDto;
import uz.pdp.codingbatapi.payload.response.ApiResponse;
import uz.pdp.codingbatapi.repository.CategoryRepository;
import uz.pdp.codingbatapi.repository.LanguageRepository;
import uz.pdp.codingbatapi.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    TaskRepository taskRepository;

    public List<Task> getMethod(){
        List<Task> tasks = taskRepository.findAll();
        return tasks;
    }
    public Task getByIdMethod(Integer id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new Task();
        return optionalTask.get();
    }
    public ApiResponse addMethod(TaskDto taskDto){
        boolean exists = taskRepository.existsByName(taskDto.getName());
        if (exists)
            return new ApiResponse("This Task is already exist",false);
        Task task=new Task();
       task.setName(taskDto.getName());
       task.setText(taskDto.getText());
       task.setMethod(taskDto.getMethod());
       task.setSolution(taskDto.getSolution());
       task.setHint(taskDto.getHint());
       task.setHasStar(taskDto.getHasStar());
        Optional<Language> optionalLanguage = languageRepository.findById(taskDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return new ApiResponse("This Language doesn't exist", false);
        Language language = optionalLanguage.get();
        task.setLanguage(language);
        taskRepository.save(task);
        return new ApiResponse("Task successfully added",true);
    }
    public ApiResponse updateMethod(TaskDto taskDto,Integer id){
        boolean exists = taskRepository.existsByNameAndIdNot(taskDto.getName(), id);
        if (exists)
            return new ApiResponse("This Task is already exist",false);
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("This Task doesn't exist", false);
        Task task = optionalTask.get();
        task.setName(taskDto.getName());
        task.setText(taskDto.getText());
        task.setMethod(taskDto.getMethod());
        task.setSolution(taskDto.getSolution());
        task.setHint(taskDto.getHint());
        task.setHasStar(taskDto.getHasStar());
        Optional<Language> optionalLanguage = languageRepository.findById(taskDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return new ApiResponse("This Language doesn't exist", false);
        Language language = optionalLanguage.get();
        task.setLanguage(language);
        taskRepository.save(task);
        return new ApiResponse("Task successfully updated",true);
    }
    public ApiResponse deleteMethod(Integer id){
        try {
            taskRepository.deleteById(id);
            return new ApiResponse("Task successfully deleted",true);
        } catch (Exception e){
            return new ApiResponse("Error in deleting",false);
        }
    }
}
