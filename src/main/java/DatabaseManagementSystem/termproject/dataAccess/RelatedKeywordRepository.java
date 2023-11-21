package DatabaseManagementSystem.termproject.dataAccess;

import DatabaseManagementSystem.termproject.entities.RelatedKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelatedKeywordRepository extends JpaRepository<RelatedKeyword, Integer> {
    boolean existsByKeywordIdIn(List<Integer> ids);
    List<RelatedKeyword> getRelatedKeywordByKeywordIdIn(List<Integer> ids);
}
