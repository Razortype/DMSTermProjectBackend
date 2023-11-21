package DatabaseManagementSystem.termproject.business.concretes;

import DatabaseManagementSystem.termproject.api.models.SubjectModel;
import DatabaseManagementSystem.termproject.business.abstracts.SubjectService;
import DatabaseManagementSystem.termproject.core.utils.results.*;
import DatabaseManagementSystem.termproject.dataAccess.SubjectRepository;
import DatabaseManagementSystem.termproject.entities.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectManager implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public DataResult<List<Subject>> getAllSubject() {
        List<Subject> subjects = subjectRepository.findAll();
        return new SuccessDataResult<>(subjects, "Subjects fetched");
    }

    @Override
    public DataResult<Subject> getSubjectById(int id) {
        Subject subject = subjectRepository.findById(id).orElse(null);
        if (subject == null) {
            return new ErrorDataResult<>("Subject not found: " + id);
        }
        return new SuccessDataResult<>(subject, "Subject found");
    }

    @Override
    public Result saveSubject(SubjectModel model) {
        if (model.getSubjectName().isEmpty()) {
            return new ErrorResult("Subject can not be empty");
        }
        return saveSubject(Subject.builder()
                .subjectName(model.getSubjectName())
                .build());
    }

    @Override
    public Result saveSubject(Subject subject) {
        try {
            subjectRepository.save(subject);
        } catch (DataIntegrityViolationException e) {
            return new ErrorResult("Subject exists: " + subject.getSubjectName());
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("Subject saved");
    }

    @Override
    public Result updateSubjectById(int id, SubjectModel model) {
        if (model.getSubjectName() == null || model.getSubjectName().isEmpty()) {
            return new ErrorResult("Subject can not be empty");
        }
        DataResult subjectResult = getSubjectById(id);
        if (!subjectResult.isSuccess()) {
            return new ErrorResult(subjectResult.getMessage());
        }
        Subject subject = (Subject) subjectResult.getData();
        subject.setSubjectName(model.getSubjectName());
        Result saveResult = saveSubject(subject);
        if (!subjectResult.isSuccess()) {
            return new ErrorResult(saveResult.getMessage());
        }
        return new SuccessResult("Subject updated");
    }

    @Override
    public Result deleteSubjectById(int id) {
        DataResult subjectResult = getSubjectById(id);
        if (!subjectResult.isSuccess()) {
            return new ErrorResult(subjectResult.getMessage());
        }
        subjectRepository.delete((Subject) subjectResult.getData());
        return new SuccessResult("Subject deleted");
    }

    @Override
    public Result isSubjectIdsExists(List<Integer> ids) {
        if (!subjectRepository.existsBySubjectIdIn(ids)) {
            return new ErrorResult("Subject(s) not exists: " + ids);
        }
        return new SuccessResult("Subject(s) exists");
    }

    @Override
    public DataResult getSubjectsByIdsIn(List<Integer> ids) {
        List<Subject> subjects = subjectRepository.getSubjectBySubjectIdIn(ids);
        if (subjects.size() != ids.size()) {
            List<Integer> notExists = subjects.stream()
                    .map(Subject::getSubjectId)
                    .filter(id -> ids.contains(id))
                    .collect(Collectors.toList());
            return new ErrorDataResult(subjects, "Subject(s) not found: " + notExists);
        }
        return new SuccessDataResult(subjects, "Subject(s) found");
    }
}
