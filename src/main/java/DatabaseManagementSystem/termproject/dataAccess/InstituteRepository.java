package DatabaseManagementSystem.termproject.dataAccess;

import DatabaseManagementSystem.termproject.entities.Institute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstituteRepository extends JpaRepository<Institute, Integer> {

    Optional<Institute> findByInstituteName(String name);

}
