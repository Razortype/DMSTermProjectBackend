package DatabaseManagementSystem.termproject.dataAccess;

import DatabaseManagementSystem.termproject.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    boolean existsBySubjectIdIn(List<Integer> ids);
    List<Subject> getSubjectBySubjectIdIn(List<Integer> ids);
}
