package DatabaseManagementSystem.termproject.business.concretes;

import DatabaseManagementSystem.termproject.api.models.RelatedKeywordModel;
import DatabaseManagementSystem.termproject.business.abstracts.RelatedKeywordService;
import DatabaseManagementSystem.termproject.core.utils.results.*;
import DatabaseManagementSystem.termproject.dataAccess.RelatedKeywordRepository;
import DatabaseManagementSystem.termproject.entities.RelatedKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatedKeywordManager implements RelatedKeywordService {

    private final RelatedKeywordRepository relatedKeywordRepository;

    @Override
    public DataResult<List<RelatedKeyword>> getAllRelatedKeyword() {
        List<RelatedKeyword> keywords = relatedKeywordRepository.findAll();
        return new SuccessDataResult<>(keywords, "Related Keywords fetched");
    }

    @Override
    public DataResult<RelatedKeyword> getRelatedKeywordById(int id) {
        RelatedKeyword relatedKeyword = relatedKeywordRepository.findById(id).orElse(null);
        if (relatedKeyword == null) {
            return new ErrorDataResult<>("Related Keyword not found:" + id);
        }
        return new SuccessDataResult<>(relatedKeyword, "Related Keyword found");
    }

    @Override
    public Result saveRelatedKeyword(RelatedKeywordModel model) {
        if (model.getRelatedKeyword() == null || model.getRelatedKeyword().isEmpty()) {
            return new ErrorResult("Related Keyword cannot be empty");
        }
        return saveRelatedKeyword(RelatedKeyword.builder()
                .relatedKeyword(model.getRelatedKeyword())
                .build());
    }

    @Override
    public Result saveRelatedKeyword(RelatedKeyword keyword) {
        try {
            relatedKeywordRepository.save(keyword);
        } catch (DataIntegrityViolationException e) {
            return new ErrorResult("Related Keyword exists: " + keyword.getRelatedKeyword());
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("Related Keyword saved");
    }

    @Override
    public Result updateRelatedKeywordById(int id, RelatedKeywordModel model) {
        if (model.getRelatedKeyword().isEmpty()) {
            return new ErrorResult("Related Keyword cannot be empty");
        }
        DataResult keywordResult = getRelatedKeywordById(id);
        if (!keywordResult.isSuccess()) {
            return new ErrorResult(keywordResult.getMessage());
        }
        RelatedKeyword keyword = (RelatedKeyword) keywordResult.getData();
        keyword.setRelatedKeyword(model.getRelatedKeyword());
        Result saveResult = saveRelatedKeyword(keyword);
        if (!saveResult.isSuccess()) {
            return new ErrorResult(saveResult.getMessage());
        }
        return new SuccessResult("Related Keyword updated");
    }

    @Override
    public Result deleteRelatedKeywordById(int id) {
        DataResult keywordResult = getRelatedKeywordById(id);
        if (!keywordResult.isSuccess()) {
            return new ErrorResult(keywordResult.getMessage());
        }
        relatedKeywordRepository.delete((RelatedKeyword) keywordResult.getData());
        return new SuccessResult("Related Keyword deleted");
    }

    @Override
    public Result isRelatedKeywordIdsExists(List<Integer> ids) {
        if(!relatedKeywordRepository.existsByKeywordIdIn(ids)) {
            return new ErrorResult("Related Keyword(s) not exists: " + ids);
        }
        return new SuccessResult("Related Keyword(s) exist");
    }

    @Override
    public DataResult getRelatedKeywordsByIdsIn(List<Integer> ids) {
        List<RelatedKeyword> keywords = relatedKeywordRepository.getRelatedKeywordByKeywordIdIn(ids);
        if (keywords.size() != ids.size()) {
            List<Integer> notExists = keywords.stream()
                    .map(RelatedKeyword::getKeywordId)
                    .filter(id -> !ids.contains(id))
                    .collect(Collectors.toList());
            return new ErrorDataResult(keywords, "Related Keyword(s) not found:" + notExists);
        }
        return new SuccessDataResult(keywords, "Related Keys(s) found");
    }
}
