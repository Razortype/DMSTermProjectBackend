package DatabaseManagementSystem.termproject.business.abstracts;

import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.ThesisLanguage;

import java.util.List;

public interface ThesisLanguageService {

    DataResult<List<ThesisLanguage>> getAllThesisLanguages();
    DataResult<ThesisLanguage> getById(int thesisLanguageId);
    Result saveNewThesisLanguage(ThesisLanguage language);

}
