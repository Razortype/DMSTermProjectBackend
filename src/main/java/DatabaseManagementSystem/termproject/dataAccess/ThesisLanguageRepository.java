package DatabaseManagementSystem.termproject.dataAccess;

import DatabaseManagementSystem.termproject.entities.ThesisLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThesisLanguageRepository extends JpaRepository<ThesisLanguage, Integer> {

    Optional<ThesisLanguage> getByThesisLanguageId(int thesisLanguageId);
    Optional<ThesisLanguage> findThesisLanguageByThesisLanguage(String language);

}
