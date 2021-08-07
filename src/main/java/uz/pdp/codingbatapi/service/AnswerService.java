package uz.pdp.codingbatapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbatapi.entity.Answer;
import uz.pdp.codingbatapi.entity.Task;
import uz.pdp.codingbatapi.entity.User;
import uz.pdp.codingbatapi.payload.AnswerDto;
import uz.pdp.codingbatapi.payload.UserDto;
import uz.pdp.codingbatapi.payload.response.ApiResponse;
import uz.pdp.codingbatapi.repository.AnswerRepository;
import uz.pdp.codingbatapi.repository.TaskRepository;
import uz.pdp.codingbatapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    AnswerRepository answerRepository;

    public List<Answer> getMethod(){
        List<Answer> answers = answerRepository.findAll();
        return answers;
    }
    public Answer getByIdMethod(Integer id){
        Optional<Answer> optionalUser = answerRepository.findById(id);
        if (!optionalUser.isPresent())
            return new Answer();
        return optionalUser.get();
    }
    public ApiResponse addMethod(AnswerDto answerDto){
        Answer answer=new Answer();
        answer.setText(answerDto.getText());
        answer.setIsCorrect(answerDto.getText());
        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("This User doesn't exist",false);
        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("This Task doesn't exist",false);

        answer.setUsers(optionalUser.get());
        answer.setTask(optionalTask.get());
        return new ApiResponse("Answer successfully added",true);
    }
    public ApiResponse updateMethod(AnswerDto answerDto,Integer id){
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (!optionalAnswer.isPresent())
            return new ApiResponse("This Answer doesn't exist", false);
        Answer answer = optionalAnswer.get();
        answer.setText(answerDto.getText());
        answer.setIsCorrect(answerDto.getText());
        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("This User doesn't exist",false);
        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("This Task doesn't exist",false);

        answer.setUsers(optionalUser.get());
        answer.setTask(optionalTask.get());
        return new ApiResponse("Answer successfully updated",true);
    }
    public ApiResponse deleteMethod(Integer id){
        try {
            answerRepository.deleteById(id);
            return new ApiResponse("Answer successfully deleted",true);
        } catch (Exception e){
            return new ApiResponse("Error in deleting",false);
        }
    }
}
