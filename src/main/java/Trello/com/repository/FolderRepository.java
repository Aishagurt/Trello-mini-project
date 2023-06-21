package Trello.com.repository;
import Trello.com.entities.Folders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FolderRepository extends JpaRepository<Folders, Long> {
}