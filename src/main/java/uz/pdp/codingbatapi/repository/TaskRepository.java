package uz.pdp.codingbatapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbatapi.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);

}
