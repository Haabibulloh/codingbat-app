package uz.pdp.codingbatapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.codingbatapi.entity.User;
import uz.pdp.codingbatapi.payload.UserDto;
import uz.pdp.codingbatapi.payload.response.ApiResponse;
import uz.pdp.codingbatapi.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getMethod(){
        List<User> users = userRepository.findAll();
        return users;
    }
    public User getByIdMethod(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new User();
        return optionalUser.get();
    }
    public ApiResponse addMethod(UserDto userDto){
        boolean exists = userRepository.existsByEmail(userDto.getEmail());
        if (exists)
            return new ApiResponse("This email is already exist",false);
        User user=new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("User successfully added",true);
    }
    public ApiResponse updateMethod(UserDto userDto,Integer id){
        boolean exists = userRepository.existsByEmailAndIdNot(userDto.getEmail(), id);
        if (exists)
            return new ApiResponse("This email is already exist",false);
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("This User doesn't exist", false);
        User user = optionalUser.get();user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("User successfully updated",true);
    }
    public ApiResponse deleteMethod(Integer id){
        try {
            userRepository.deleteById(id);
            return new ApiResponse("User successfully deleted",true);
        } catch (Exception e){
            return new ApiResponse("Error in deleting",false);
        }
    }
}
