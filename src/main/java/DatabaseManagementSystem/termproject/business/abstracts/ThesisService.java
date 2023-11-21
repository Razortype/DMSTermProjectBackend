package DatabaseManagementSystem.termproject.business.abstracts;

import DatabaseManagementSystem.termproject.api.models.ThesisModel;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.Thesis;

import java.util.List;

public interface ThesisService {
    DataResult<List<Thesis>> getAllThesis();
    DataResult<List<Thesis>> getAllThesisByUser();
    DataResult<Thesis> getThesisById(int thesisId);
    Result saveNewThesis(ThesisModel model);
    Result deleteById(int thesisId);
    Result deleteThesisOwnByUser(int thesisId);
    Result addSubjectToThesisByOwner(int thesisId, int subjectId);
    Result removeSubjectFromThesisByOwner(int thesisId, int subjectId);
    Result addKeywordToThesisByOwner(int thesisId, int keywordId);
    Result removeKeywordFromThesisByOwner(int thesisId, int keywordId);
    Result addSubjectToThesis(int thesisId, int subjectId);
    Result removeSubjectFromThesis(int thesisId, int subjectId);
    Result addKeywordToThesis(int thesisId, int keywordId);
    Result removeKeywordFromThesis(int thesisId, int keywordId);

    /*
    Result addSupervisorToThesis(int thesisId, int userId);
    Result removeSupervisorFromThesis(int thesisId, int userId);
    Result addSupervisorToThesisByOwner(int thesisId, int userId);
    Result removeSupervisorFromThesisByOwner(int thesisId, int userId);
     */
}
