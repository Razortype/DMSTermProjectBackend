package DatabaseManagementSystem.termproject.business.concretes;

import DatabaseManagementSystem.termproject.api.models.InstituteModel;
import DatabaseManagementSystem.termproject.business.abstracts.InstituteService;
import DatabaseManagementSystem.termproject.core.utils.results.*;
import DatabaseManagementSystem.termproject.dataAccess.InstituteRepository;
import DatabaseManagementSystem.termproject.entities.Institute;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstituteManager implements InstituteService {

    private final InstituteRepository instituteRepository;

    @Override
    public DataResult<List<Institute>> getAllInstitute() {
        List<Institute> institutes = instituteRepository.findAll();
        return new SuccessDataResult<>(institutes, "Institutes fetched");
    }

    @Override
    public DataResult<Institute> getInstituteById(int id) {
        Institute institute = instituteRepository.findById(id).orElse(null);
        if (institute == null) {
            return new ErrorDataResult<>("Institute not found: " + id);
        }
        return new SuccessDataResult<>(institute, "Institute found");
    }

    @Override
    public DataResult<Institute> getInstituteByInstituteName(String name) {
        Institute institute = instituteRepository.findByInstituteName(name).orElse(null);
        if (institute == null) {
            return new ErrorDataResult<>("Institute not found: " + name);
        }
        return new SuccessDataResult<>(institute, "Institute found");
    }

    @Override
    public Result saveInstitute(InstituteModel model) {
        if (model.getInstituteName() == null || model.getInstituteName().isEmpty()) {
            return new ErrorResult("Institute name can not be empty");
        }
        return saveInstitute(instituteRepository.save(
                Institute.builder()
                        .instituteName(model.getInstituteName())
                        .build()
        ));
    }

    @Override
    public Result saveInstitute(Institute institute) {
        try {
            instituteRepository.save(institute);
        } catch (DataIntegrityViolationException e) {
            return new ErrorResult("Institute exists: " + institute.getInstituteName());
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("Institute saved");
    }

    @Override
    public Result updateInstituteById(int id, InstituteModel model) {
        DataResult instituteResult = getInstituteById(id);
        if (!instituteResult.isSuccess()) {
            return new ErrorResult(instituteResult.getMessage());
        }
        if (model.getInstituteName().isEmpty()) {
            return new ErrorResult("Institute name can not be empty");
        }
        Institute institute = (Institute) instituteResult.getData();
        institute.setInstituteName(model.getInstituteName());
        Result saveResult = saveInstitute(institute);
        if (!saveResult.isSuccess()) {
            return new ErrorResult(saveResult.getMessage());
        }
        return new SuccessResult("Institute updated");
    }

    @Override
    public Result deleteInstituteById(int id) {
        DataResult instituteResult = getInstituteById(id);
        if (!instituteResult.isSuccess()) {
            return new ErrorResult(instituteResult.getMessage());
        }
        instituteRepository.delete((Institute) instituteResult.getData());
        return new SuccessResult("Institute deleted");
    }
}
