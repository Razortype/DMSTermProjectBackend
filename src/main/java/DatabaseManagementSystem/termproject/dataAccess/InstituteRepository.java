package DatabaseManagementSystem.termproject.dataAccess;

import DatabaseManagementSystem.termproject.entities.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstituteRepository extends JpaRepository<Institute, Integer> {
}
