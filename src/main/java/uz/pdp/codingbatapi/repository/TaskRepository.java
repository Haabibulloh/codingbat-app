package uz.pdp.codingbatapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbatapi.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    boolean existsByNameAndCategory_Id(String name, Integer category_id);
    boolean existsByNameAndCategory_IdAndIdNot(String name, Integer category_id, Integer id);

}
