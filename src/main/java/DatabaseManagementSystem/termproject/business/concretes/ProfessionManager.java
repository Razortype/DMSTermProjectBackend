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
    public DataResult<Profession> getByProfessionName(String name) {
        Profession profession = professionRepository.findProfessionByProfessionName(name).orElse(null);
        if (profession == null) {
            return new ErrorDataResult<>("Profession not found: " + name);
        }
        return new SuccessDataResult<>(profession, "Profession fetched");
    }

    @Override
    public Result saveNewProfession(ProfessionModel model) {
        return saveProfession(
                Profession.builder()
                        .professionName(model.getProfessionName())
                        .professionDescription(model.getProfessionDescription())
                        .build()
        );
    }

    @Override
    public Result saveProfession(Profession profession) {
        try {
            professionRepository.save(profession);
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("Profession saved");
    }

    @Override
    public Result updateProfessionById(int professionId, ProfessionModel model) {
        if (model.getProfessionName().isEmpty() || model.getProfessionDescription().isEmpty()) {
            return new ErrorResult("Profession field(s) could not be empty");
        }
        DataResult result = getById(professionId);
        if (!result.isSuccess()) {
            return new ErrorResult(result.getMessage());
        }
        Profession profession = (Profession) result.getData();
        profession.setProfessionName(model.getProfessionName());
        profession.setProfessionDescription(model.getProfessionDescription());

        Result saveResult = saveProfession(profession);
        if (!saveResult.isSuccess()) {
            return new ErrorResult(saveResult.getMessage());
        }
        return new SuccessResult("Profession updated");
    }

    @Override
    public Result deleteProfessionById(int professionId) {
        DataResult result = getById(professionId);
        if (!result.isSuccess()) {
            return new ErrorResult(result.getMessage());
        }
        Profession profession = (Profession) result.getData();
        return deleteProfession(profession);
    }

    @Override
    public Result deleteProfession(Profession profession) {
        try {
            professionRepository.delete(profession);
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("Profession deleted");
    }
}
