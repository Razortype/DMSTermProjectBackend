package DatabaseManagementSystem.termproject.dataAccess;

import DatabaseManagementSystem.termproject.entities.ThesisType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThesisTypeRepository extends JpaRepository<ThesisType, Integer> {

    Optional<ThesisType> getByThesisTypeId(int thesisTypeId);
    Optional<ThesisType> findByThesisTypeName(String name);

}
