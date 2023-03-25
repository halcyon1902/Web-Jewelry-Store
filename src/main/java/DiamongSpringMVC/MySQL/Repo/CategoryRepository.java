package DiamongSpringMVC.MySQL.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import DiamongSpringMVC.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query(value = "select * from category where category_id = ?1 and category_name = ?2", nativeQuery = true)
	public Category findByIdAndName(Long id, String name);

}
