package DatabaseManagementSystem.termproject.business.concretes;

import DatabaseManagementSystem.termproject.api.models.ThesisLanguageModel;
import DatabaseManagementSystem.termproject.business.abstracts.ThesisLanguageService;
import DatabaseManagementSystem.termproject.core.utils.results.*;
import DatabaseManagementSystem.termproject.dataAccess.ThesisLanguageRepository;
import DatabaseManagementSystem.termproject.entities.ThesisLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThesisLanguageManager implements ThesisLanguageService {

    private final ThesisLanguageRepository thesisLanguageRepository;

    @Override
    public DataResult<List<ThesisLanguage>> getAllThesisLanguages() {
        List<ThesisLanguage> thesisLanguages = thesisLanguageRepository.findAll();
        return new SuccessDataResult<>(thesisLanguages, "All Thesis Language fetched");
    }

    @Override
    public DataResult<ThesisLanguage> getById(int thesisLanguageId) {
        ThesisLanguage language = thesisLanguageRepository.findById(thesisLanguageId).orElse(null);
        if (language == null) {
            return new ErrorDataResult<>("Thesis Language not found: " + thesisLanguageId);
        }
        return new SuccessDataResult<>(language, "Thesis Language fetched");
    }

    @Override
    public DataResult<ThesisLanguage> getByLanguage(String language) {
        ThesisLanguage languageObj = thesisLanguageRepository.findThesisLanguageByThesisLanguage(language).orElse(null);
        if (language == null) {
            return new ErrorDataResult<>("Thesis Language not found: " + language);
        }
        return new SuccessDataResult<>(languageObj, "Thesis Language fetched");
    }

    @Override
    public Result saveNewThesisLanguage(ThesisLanguageModel model) {
        if (model.getLanguageName().isEmpty()) {
            return new ErrorResult("Thesis Language field(s) could not be empty");
        }
        return saveThesisLanguage(
                ThesisLanguage.builder()
                        .thesisLanguage(model.getLanguageName())
                        .build()
        );
    }

    @Override
    public Result saveThesisLanguage(ThesisLanguage language) {
        try {
            thesisLanguageRepository.save(language);
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("Thesis Language saved");
    }

    @Override
    public Result updateThesisLanguage(int thesisLanguageId, ThesisLanguageModel model) {
        DataResult result = getById(thesisLanguageId);
        if (!result.isSuccess()) {
            return new ErrorResult(result.getMessage());
        }
        ThesisLanguage thesisLanguage = (ThesisLanguage) result.getData();
        thesisLanguage.setThesisLanguage(model.getLanguageName());

        Result saveResult = saveThesisLanguage(thesisLanguage);
        if (!saveResult.isSuccess()) {
            return new ErrorResult(saveResult.getMessage());
        }
        return new SuccessResult("Thesis Language updated");
    }

    @Override
    public Result deleteThesisLanguage(int thesisLanguageId) {
        DataResult result = getById(thesisLanguageId);
        if (!result.isSuccess()) {
            return new ErrorResult(result.getMessage());
        }
        return deleteThesisLanguage((ThesisLanguage) result.getData());
    }

    @Override
    public Result deleteThesisLanguage(ThesisLanguage thesisLanguage) {
        try {
            thesisLanguageRepository.delete(thesisLanguage);
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("Thesis Language deleted");
    }
}
