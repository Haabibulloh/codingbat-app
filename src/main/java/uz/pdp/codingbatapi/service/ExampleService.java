package uz.pdp.codingbatapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbatapi.entity.Category;
import uz.pdp.codingbatapi.entity.Example;
import uz.pdp.codingbatapi.entity.Task;
import uz.pdp.codingbatapi.payload.CategoryDto;
import uz.pdp.codingbatapi.payload.ExampleDto;
import uz.pdp.codingbatapi.payload.TaskDto;
import uz.pdp.codingbatapi.payload.response.ApiResponse;
import uz.pdp.codingbatapi.repository.CategoryRepository;
import uz.pdp.codingbatapi.repository.ExampleRepository;
import uz.pdp.codingbatapi.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExampleService {
    @Autowired
    ExampleRepository exampleRepository;
    @Autowired
    TaskRepository taskRepository;

    public List<Example> getMethod(){
        List<Example> examples = exampleRepository.findAll();
        return examples;
    }
    public Example getByIdMethod(Integer id){
        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (!optionalExample.isPresent())
            return new Example();
        return optionalExample.get();
    }
    public ApiResponse addMethod(ExampleDto exampleDto){

        Example example=new Example();
        example.setText(exampleDto.getText());
        Optional<Task> optionalTask = taskRepository.findById(exampleDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("This Task doesn't exist",false);
        example.setTask(optionalTask.get());
        exampleRepository.save(example);
        return new ApiResponse("Task successfully added",true);
    }
    public ApiResponse updateMethod(ExampleDto exampleDto, Integer id){

        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (!optionalExample.isPresent())
            return new ApiResponse("This Example doesn't exist", false);
        Example example = optionalExample.get();
        example.setText(exampleDto.getText());
        Optional<Task> optionalTask = taskRepository.findById(exampleDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("This Task doesn't exist",false);
        example.setTask(optionalTask.get());
        exampleRepository.save(example);
        return new ApiResponse("Example successfully updated",true);
    }
    public ApiResponse deleteMethod(Integer id){
        try {
            exampleRepository.deleteById(id);
            return new ApiResponse("Example successfully deleted",true);
        } catch (Exception e){
            return new ApiResponse("Error in deleting",false);
        }
    }
}
