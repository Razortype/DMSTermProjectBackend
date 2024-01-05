package DatabaseManagementSystem.termproject.business.concretes;

import DatabaseManagementSystem.termproject.api.models.ThesisTypeModel;
import DatabaseManagementSystem.termproject.business.abstracts.ThesisTypeService;
import DatabaseManagementSystem.termproject.core.utils.results.*;
import DatabaseManagementSystem.termproject.dataAccess.ThesisTypeRepository;
import DatabaseManagementSystem.termproject.entities.ThesisType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThesisTypeManager implements ThesisTypeService {

    private final ThesisTypeRepository thesisTypeRepository;

    @Override
    public DataResult<List<ThesisType>> getAllThesisTypes() {
        List<ThesisType> types = thesisTypeRepository.findAll();
        return new SuccessDataResult<>(types, "All Thesis Types fetched");
    }

    @Override
    public DataResult<ThesisType> getById(int thesisTypeId) {
        ThesisType type = thesisTypeRepository.findById(thesisTypeId).orElse(null);
        if (type == null) {
            return new ErrorDataResult<>("Thesis Type not found: " + thesisTypeId);
        }
        return new SuccessDataResult<>(type, "Thesis Type fetched");

    }

    @Override
    public DataResult<ThesisType> getByThesisTypeName(String name) {
        ThesisType type = thesisTypeRepository.findByThesisTypeName(name).orElse(null);
        if (type == null) {
            return new ErrorDataResult<>("Thesis Type not found: " + name);
        }
        return new SuccessDataResult<>(type, "Thesis Type fetched");
    }

    @Override
    public Result saveNewThesisType(ThesisTypeModel model) {
        if (model.getTypeName().isEmpty() || model.getTypeDescription().isEmpty()) {
            return new ErrorResult("Thesis Type field(s) could not be empty");
        }
        return saveThesisType(
                ThesisType.builder()
                        .thesisTypeName(model.getTypeName())
                        .thesisTypeDescription(model.getTypeDescription())
                        .build()
        );
    }

    @Override
    public Result saveThesisType(ThesisType type) {
        try {
            thesisTypeRepository.save(type);
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred:" + e.getMessage());
        }
        return new SuccessResult("Thesis Type saved");
    }

    @Override
    public Result updateThesisType(int thesisTypeId, ThesisTypeModel model) {
        if (model.getTypeName().isEmpty() || model.getTypeDescription().isEmpty()) {
            return new ErrorResult("Thesis Type field(s) could not be empty");
        }
        DataResult result = getById(thesisTypeId);
        if (!result.isSuccess()) {
            return new ErrorResult(result.getMessage());
        }
        ThesisType type = (ThesisType) result.getData();
        type.setThesisTypeName(model.getTypeName());
        type.setThesisTypeDescription(model.getTypeDescription());

        Result saveResult = saveThesisType(type);
        if (!saveResult.isSuccess()) {
            return new ErrorResult(saveResult.getMessage());
        }
        return new SuccessResult("Thesis Type updated");
    }

    @Override
    public Result deleteThesisType(int thesisTypeId) {
        DataResult result = getById(thesisTypeId);
        if (!result.isSuccess()) {
            return new ErrorResult(result.getMessage());
        }
        return deleteThesisType((ThesisType) result.getData());
    }

    @Override
    public Result deleteThesisType(ThesisType type) {
        try {
            thesisTypeRepository.delete(type);
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("These Type deleted");
    }
}
