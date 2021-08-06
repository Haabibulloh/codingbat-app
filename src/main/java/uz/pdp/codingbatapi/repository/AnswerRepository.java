package uz.pdp.codingbatapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbatapi.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    boolean existsByText(String text);
    boolean existsByTextAndIdNot(String text, Integer id);
}
