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
    CategoryRepository categoryRepository;
    @Autowired
    TaskRepository taskRepository;

    public List<Task> getMethod() {
        List<Task> tasks = taskRepository.findAll();
        return tasks;
    }

    public Task getByIdMethod(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new Task();
        return optionalTask.get();
    }

    public ApiResponse addMethod(TaskDto taskDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(taskDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new ApiResponse("This Category doesn't exist", false);
        boolean exists = taskRepository.existsByNameAndCategory_Id(taskDto.getName(), taskDto.getCategoryId());
        if (exists)
            return new ApiResponse("This Task is already exist", false);
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setText(taskDto.getText());
        task.setMethod(taskDto.getMethod());
        task.setSolution(taskDto.getSolution());
        task.setHint(taskDto.getHint());
        task.setHasStar(taskDto.getHasStar());

        Category category = optionalCategory.get();
        task.setCategory(category);
        taskRepository.save(task);
        return new ApiResponse("Task successfully added", true);
    }

    public ApiResponse updateMethod(TaskDto taskDto, Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("This Task doesn't exist", false);
        Optional<Category> optionalCategory = categoryRepository.findById(taskDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new ApiResponse("This Category doesn't exist", false);
        boolean exists = taskRepository.existsByNameAndCategory_IdAndIdNot(taskDto.getName(), taskDto.getCategoryId(), id);
        if (exists)
            return new ApiResponse("This Task is already exist", false);

        Task task = optionalTask.get();
        task.setName(taskDto.getName());
        task.setText(taskDto.getText());
        task.setMethod(taskDto.getMethod());
        task.setSolution(taskDto.getSolution());
        task.setHint(taskDto.getHint());
        task.setHasStar(taskDto.getHasStar());

        Category category = optionalCategory.get();
        task.setCategory(category);
        taskRepository.save(task);
        return new ApiResponse("Task successfully updated", true);
    }

    public ApiResponse deleteMethod(Integer id) {
        try {
            taskRepository.deleteById(id);
            return new ApiResponse("Task successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }
}
