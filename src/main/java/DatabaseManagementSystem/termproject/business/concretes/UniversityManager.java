package DatabaseManagementSystem.termproject.business.concretes;

import DatabaseManagementSystem.termproject.api.models.UniversityModel;
import DatabaseManagementSystem.termproject.business.abstracts.UniversityService;
import DatabaseManagementSystem.termproject.core.utils.results.*;
import DatabaseManagementSystem.termproject.dataAccess.UniversityRepository;
import DatabaseManagementSystem.termproject.entities.University;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityManager implements UniversityService {

    private final UniversityRepository universityRepository;

    @Override
    public DataResult<List<University>> getAllUniversity() {
        List<University> universities = universityRepository.findAll();
        return new SuccessDataResult<>(universities, "Universities fetched");
    }

    @Override
    public DataResult<University> getUniversityById(int id) {
        University university = universityRepository.findById(id).orElse(null);
        if (university == null) {
            return new ErrorDataResult<>("University not found: " + id);
        }
        return new SuccessDataResult<>(university, "University found");
    }

    @Override
    public DataResult<University> getUniversityByUniversityName(String name) {
        University university = universityRepository.findByUniversityName(name).orElse(null);
        if (university == null) {
            return new ErrorDataResult<>("University not found: " + name);
        }
        return new SuccessDataResult<>(university, "University found");
    }

    @Override
    public Result saveUniversity(UniversityModel model) {
        if (model.getUniversityName() == null || model.getUniversityName().isEmpty()) {
            return new ErrorResult("University name can not be empty");
        }
        return saveUniversity(University.builder()
                .universityName(model.getUniversityName())
                .build());
    }

    @Override
    public Result saveUniversity(University university) {
        try {
            universityRepository.save(university);
        } catch (DataIntegrityViolationException e) {
            return new ErrorResult("University exists: " + university.getUniversityName());
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("University saved");
    }

    @Override
    public Result updateUniversityById(int id, UniversityModel model) {
        if (model.getUniversityName().isEmpty()) {
            return new ErrorResult("University name can not be empty");
        }
        DataResult universityResult = getUniversityById(id);
        if (!universityResult.isSuccess()) {
            return new ErrorResult(universityResult.getMessage());
        }
        University university = (University) universityResult.getData();
        university.setUniversityName(model.getUniversityName());
        Result saveResult = saveUniversity(university);
        if (!saveResult.isSuccess()) {
            return new ErrorResult(saveResult.getMessage());
        }
        return new SuccessResult("University updated");
    }

    @Override
    public Result deleteUniversityById(int id) {
        DataResult universityResult = getUniversityById(id);
        if (!universityResult.isSuccess()) {
            return new ErrorResult(universityResult.getMessage());
        }
        universityRepository.delete((University) universityResult.getData());
        return new SuccessResult("University deleted");
    }
}
