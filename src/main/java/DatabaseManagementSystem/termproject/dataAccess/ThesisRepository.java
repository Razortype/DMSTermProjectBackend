package DatabaseManagementSystem.termproject.dataAccess;

import DatabaseManagementSystem.termproject.entities.*;
import DatabaseManagementSystem.termproject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ThesisRepository extends JpaRepository<Thesis, Integer> {
    List<Thesis> getAllByAuthor(User user);

    @Query("""
    select t from Thesis t \s
    where (:word is null or t.title like %:word%) \s
    and (:keywords is null or t.relatedKeywords in :keywords) \s
    and (:subjects is null or t.subjects in :subjects) \s
    and (:universities is null or t.university.universityId in :universities) \s
    and (:institutes is null or t.institute.instituteId in :institutes) \s
    and (:users is null or t.author.userId in :users or t.supervisor.userId in :users or t.coSupervisor.userId in :users) \s
    and (:languages is null or t.language.thesisLanguageId in :languages) \s
    and (:types is null or t.type.thesisTypeId in :types) \s
    """)
    List<Thesis> findBySearchQuery(String word,
                                   List<Integer> keywords,
                                   List<Integer> subjects,
                                   List<Integer> universities,
                                   List<Integer> institutes,
                                   List<Integer> users,
                                   List<Integer> languages,
                                   List<Integer> types);
}
