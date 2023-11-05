package DatabaseManagementSystem.termproject.business.concretes;

import DatabaseManagementSystem.termproject.api.models.ProfessionModel;
import DatabaseManagementSystem.termproject.business.abstracts.ProfessionService;
import DatabaseManagementSystem.termproject.core.utils.results.*;
import DatabaseManagementSystem.termproject.dataAccess.ProfessionRepository;
import DatabaseManagementSystem.termproject.entities.Profession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionManager implements ProfessionService {

    private final ProfessionRepository professionRepository;

    @Override
    public DataResult<List<Profession>> getAllProfessions() {
        List<Profession> professions = professionRepository.findAll();
        return new SuccessDataResult<>(professions, "All Professions fetched");
    }

    @Override
    public DataResult<Profession> getById(int professionId) {
        Profession profession = professionRepository.findById(professionId).orElse(null);
        if (profession == null) {
            return new ErrorDataResult("Profession not found: " + professionId);
        }
        return new SuccessDataResult(profession, "Profession fetched");
    }

    @Override
    public Result saveNewProfession(ProfessionModel model) {
        return saveNewProfession(
                Profession.builder()
                        .professionName(model.getProfessionName())
                        .professionDescription(model.getProfessionDescription())
                        .build()
        );
    }

    @Override
    public Result saveNewProfession(Profession profession) {
        try {
            professionRepository.save(profession);
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("Profession saved");
    }
}
