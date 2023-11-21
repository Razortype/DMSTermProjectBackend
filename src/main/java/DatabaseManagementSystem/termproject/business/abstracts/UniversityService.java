package DatabaseManagementSystem.termproject.business.abstracts;

import DatabaseManagementSystem.termproject.api.models.UniversityModel;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.University;

import java.util.List;

public interface UniversityService {
    DataResult<List<University>> getAllUniversity();
    DataResult<University> getUniversityById(int id);
    Result saveUniversity(UniversityModel model);
    Result saveUniversity(University university);
    Result updateUniversityById(int id, UniversityModel model);
    Result deleteUniversityById(int id);
}
