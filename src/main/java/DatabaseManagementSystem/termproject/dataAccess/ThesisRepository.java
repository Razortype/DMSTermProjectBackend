package DatabaseManagementSystem.termproject.dataAccess;

import DatabaseManagementSystem.termproject.entities.Thesis;
import DatabaseManagementSystem.termproject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThesisRepository extends JpaRepository<Thesis, Integer> {
    List<Thesis> getAllByAuthor(User user);
}
