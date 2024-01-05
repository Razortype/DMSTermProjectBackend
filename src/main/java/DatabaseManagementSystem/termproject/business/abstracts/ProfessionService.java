package DatabaseManagementSystem.termproject.business.abstracts;

import DatabaseManagementSystem.termproject.api.models.ProfessionModel;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.Profession;

import java.util.List;

public interface ProfessionService {

    DataResult<List<Profession>> getAllProfessions();
    DataResult<Profession> getById(int professionId);
    DataResult<Profession> getByProfessionName(String name);
    Result saveNewProfession(ProfessionModel model);
    Result saveProfession(Profession profession);
    Result updateProfessionById(int professionId, ProfessionModel model);
    Result deleteProfessionById(int professionId);
    Result deleteProfession(Profession profession);

}
