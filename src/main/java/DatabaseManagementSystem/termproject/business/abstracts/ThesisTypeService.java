package DatabaseManagementSystem.termproject.business.abstracts;

import DatabaseManagementSystem.termproject.api.models.ThesisTypeModel;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.ThesisType;

import java.util.List;

public interface ThesisTypeService {

    DataResult<List<ThesisType>> getAllThesisTypes();
    DataResult<ThesisType> getById(int thesisTypeId);
    Result saveNewThesisType (ThesisTypeModel model);
    Result saveThesisType(ThesisType type);
    Result updateThesisType(int thesisTypeId, ThesisTypeModel model);
    Result deleteThesisType(int thesisTypeId);
    Result deleteThesisType(ThesisType type);

}
