package Trello.com.repository;
import Trello.com.entities.TaskCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<TaskCategories, Long> {
}
