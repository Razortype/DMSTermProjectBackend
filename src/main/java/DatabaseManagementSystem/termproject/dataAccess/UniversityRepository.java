package DatabaseManagementSystem.termproject.dataAccess;

import DatabaseManagementSystem.termproject.entities.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Integer> {
}
