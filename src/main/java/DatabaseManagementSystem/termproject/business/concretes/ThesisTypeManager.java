package DatabaseManagementSystem.termproject.business.concretes;

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
    public Result saveNewThesisType(ThesisType type) {
        try {
            thesisTypeRepository.save(type);
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred:" + e.getMessage());
        }
        return new SuccessResult("Thesis Type saved");
    }
}
