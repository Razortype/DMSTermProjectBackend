package DatabaseManagementSystem.termproject.business.abstracts;

import DatabaseManagementSystem.termproject.api.models.ThesisLanguageModel;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.ThesisLanguage;

import java.util.List;

public interface ThesisLanguageService {

    DataResult<List<ThesisLanguage>> getAllThesisLanguages();
    DataResult<ThesisLanguage> getById(int thesisLanguageId);
    DataResult<ThesisLanguage> getByLanguage(String language);
    Result saveNewThesisLanguage(ThesisLanguageModel model);
    Result saveThesisLanguage(ThesisLanguage language);
    Result updateThesisLanguage(int thesisLanguageId, ThesisLanguageModel model);
    Result deleteThesisLanguage(int thesisLanguageId);
    Result deleteThesisLanguage(ThesisLanguage thesisLanguage);

}
