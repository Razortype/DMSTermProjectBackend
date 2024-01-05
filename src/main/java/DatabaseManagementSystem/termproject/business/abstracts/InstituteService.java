package DatabaseManagementSystem.termproject.business.abstracts;

import DatabaseManagementSystem.termproject.api.models.InstituteModel;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.Institute;

import java.util.List;

public interface InstituteService {
    DataResult<List<Institute>> getAllInstitute();
    DataResult<Institute> getInstituteById(int id);
    DataResult<Institute> getInstituteByInstituteName(String name);
    Result saveInstitute(InstituteModel model);
    Result saveInstitute(Institute institute);
    Result updateInstituteById(int id, InstituteModel model);
    Result deleteInstituteById(int id);
}
