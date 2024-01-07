package DatabaseManagementSystem.termproject.dataAccess;

import DatabaseManagementSystem.termproject.entities.*;
import DatabaseManagementSystem.termproject.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThesisRepository extends JpaRepository<Thesis, Integer> {
    List<Thesis> getAllByAuthor(User user);

    @Query("SELECT DISTINCT t FROM Thesis t " +
            "LEFT JOIN t.relatedKeywords rk " +
            "LEFT JOIN t.subjects s " +
            "WHERE (:word IS NULL OR LOWER(t.title) LIKE %:word% OR LOWER(t.thesisAbstract) LIKE %:word%) " +
            "AND (:keywords IS NULL OR rk.keywordId IN :keywords) " +
            "AND (:subjects IS NULL OR s.subjectId IN :subjects) " +
            "AND (:universities IS NULL OR t.university.universityId IN :universities) " +
            "AND (:institutes IS NULL OR t.institute.instituteId IN :institutes) " +
            "AND (:users IS NULL OR t.author.userId IN :users) " +
            "AND (:languages IS NULL OR t.language.thesisLanguageId IN :languages) " +
            "AND (:types IS NULL OR t.type.thesisTypeId IN :types)" +
            "AND (:author IS NULL OR t.author = :author)")
    List<Thesis> findBySearchQuery(
            @Param("word") String word,
            @Param("keywords") List<Integer> keywords,
            @Param("subjects") List<Integer> subjects,
            @Param("universities") List<Integer> universities,
            @Param("institutes") List<Integer> institutes,
            @Param("users") List<Integer> users,
            @Param("languages") List<Integer> languages,
            @Param("types") List<Integer> types,
            @Param("author") User author,
            Pageable pageable
    );

    @Query("SELECT t.thesisId from Thesis t")
    List<Integer> getThesisIdList();

    List<Thesis> findAllByThesisIdIn(List<Integer> ids);

    Boolean existsByThesisNo(String thesisNo);

    @Query("SELECT t FROM Thesis t ORDER BY t.submissionDate DESC")
    List<Thesis> findLastNThesis(int n);

}
