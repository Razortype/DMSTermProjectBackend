package DatabaseManagementSystem.termproject.business.concretes;

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
    public Result saveNewThesisLanguage(ThesisLanguage language) {
        try {
            thesisLanguageRepository.save(language);
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("Thesis Language saved");
    }
}
