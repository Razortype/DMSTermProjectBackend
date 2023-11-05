package DatabaseManagementSystem.termproject.business.abstracts;

import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.ThesisType;

import java.util.List;

public interface ThesisTypeService {

    DataResult<List<ThesisType>> getAllThesisTypes();
    DataResult<ThesisType> getById(int thesisTypeId);
    Result saveNewThesisType(ThesisType type);

}
