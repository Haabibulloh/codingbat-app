package uz.pdp.codingbatapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbatapi.entity.Example;

public interface ExampleRepository extends JpaRepository<Example,Integer> {

}
