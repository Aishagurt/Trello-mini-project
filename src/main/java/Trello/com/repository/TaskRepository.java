package Trello.com.repository;
import Trello.com.entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Tasks, Long> {
}