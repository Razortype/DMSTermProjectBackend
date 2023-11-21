package DatabaseManagementSystem.termproject.business.abstracts;

import DatabaseManagementSystem.termproject.api.models.RelatedKeywordModel;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.RelatedKeyword;

import java.util.List;

public interface RelatedKeywordService {
    DataResult<List<RelatedKeyword>> getAllRelatedKeyword();
    DataResult<RelatedKeyword> getRelatedKeywordById(int id);
    Result saveRelatedKeyword(RelatedKeywordModel model);
    Result saveRelatedKeyword(RelatedKeyword keyword);
    Result updateRelatedKeywordById(int id, RelatedKeywordModel model);
    Result deleteRelatedKeywordById(int id);
    Result isRelatedKeywordIdsExists(List<Integer> ids);
    DataResult getRelatedKeywordsByIdsIn(List<Integer> ids);
}
