package uz.pdp.codingbatapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbatapi.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByNameAndLanguage_Id(String name, Integer language_id);
    boolean existsByNameAndLanguage_IdAndIdNot(String name, Integer language_id, Integer id);
}
