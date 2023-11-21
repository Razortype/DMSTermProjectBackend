package DatabaseManagementSystem.termproject.business.abstracts;

import DatabaseManagementSystem.termproject.api.models.SubjectModel;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.Subject;

import java.util.List;

public interface SubjectService {
    DataResult<List<Subject>> getAllSubject();
    DataResult<Subject> getSubjectById(int id);
    Result saveSubject(SubjectModel model);
    Result saveSubject(Subject subject);
    Result updateSubjectById(int id, SubjectModel model);
    Result deleteSubjectById(int id);
    Result isSubjectIdsExists(List<Integer> ids);
    DataResult getSubjectsByIdsIn(List<Integer> ids);
}
