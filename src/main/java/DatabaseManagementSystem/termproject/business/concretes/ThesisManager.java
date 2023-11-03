package DatabaseManagementSystem.termproject.business.concretes;

import DatabaseManagementSystem.termproject.api.models.ThesisModel;
import DatabaseManagementSystem.termproject.business.abstracts.ThesisService;
import DatabaseManagementSystem.termproject.business.abstracts.UserService;
import DatabaseManagementSystem.termproject.core.enums.TextLanguage;
import DatabaseManagementSystem.termproject.core.enums.ThesisType;
import DatabaseManagementSystem.termproject.core.utils.results.*;
import DatabaseManagementSystem.termproject.dataAccess.ThesisRepository;
import DatabaseManagementSystem.termproject.entities.Thesis;
import DatabaseManagementSystem.termproject.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThesisManager implements ThesisService {

    private final ThesisRepository thesisRepo;
    private final UserService userService;

    @Override
    public DataResult<List<Thesis>> getAllThesis() {
        List<Thesis> thesis = thesisRepo.findAll();
        return new SuccessDataResult<>(thesis, "All Thesis fetched");
    }

    @Override
    public DataResult<List<Thesis>> getAllThesisByUser() {
        DataResult userResult = userService.getUserByEmail(
                SecurityContextHolder.getContext().
                        getAuthentication().getName());
        List<Thesis> thesisList = thesisRepo.getAllByAuthor((User) userResult.getData());
        return new SuccessDataResult<>(thesisList, "All thesis fetched by Owner");
    }

    @Override
    public DataResult<Thesis> getThesisById(int thesisId) {
        Thesis thesis = thesisRepo.findById(thesisId).orElse(null);
        if (thesis == null) {
            return new ErrorDataResult<>("Thesis not found: " + thesisId);
        }
        return new SuccessDataResult<>(thesis, "Thesis fetched");
    }

    @Override
    public Result saveNewThesis(ThesisModel model) {
        ThesisType type;
        TextLanguage language;
        try {
            type = ThesisType.valueOf(model.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ErrorResult("Thesis Type not exists: " + model.getType());
        }
        try {
            language = TextLanguage.valueOf(model.getLanguage().toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ErrorResult("Text Language not exists: " + model.getLanguage());
        }

        DataResult userResult = userService.getUserByEmail(
                SecurityContextHolder.getContext().
                        getAuthentication().getName());

        try {
            thesisRepo.save(
                    Thesis.builder()
                            .thesisNo(model.getThesisNo())
                            .title(model.getTitle())
                            .thesisAbstract(model.getThesisAbstract())
                            .year(model.getYear())
                            .university(model.getUniversity())
                            .institute(model.getInstitute())
                            .numberOfPages(model.getNumberOfPages())
                            .relatedKeywords(model.getRelatedKeywords())
                            .type(type)
                            .language(language)
                            .author((User) userResult.getData())
                            .build()
            );
        } catch (DataIntegrityViolationException e) {
            return new ErrorResult("Thesis-No exists: " + model.getThesisNo());
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }

        return new SuccessResult("Thesis created");

    }

    @Override
    public Result deleteById(int thesisId) {
        DataResult result = getThesisById(thesisId);
        if (!result.isSuccess()) {
            return new ErrorResult(result.getMessage());
        }

        Thesis thesis = (Thesis) result.getData();
        try {
            thesisRepo.delete(thesis);
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred:" + e.getMessage());
        }

        return new SuccessResult("Thesis deleted: " + thesis.getThesisNo());
    }

    @Override
    public Result deleteThesisOwnByUser(int thesisId) {
        DataResult result = getThesisById(thesisId);
        if (!result.isSuccess()) {
            return new ErrorResult(result.getMessage());
        }
        Thesis thesis = (Thesis) result.getData();

        DataResult userResult = userService.getUserByEmail(
                SecurityContextHolder.getContext().
                        getAuthentication().getName());
        User authUser = (User)userResult.getData();

        if (thesis.getAuthor().getEmail() != authUser.getEmail() || thesis.getSupervisors().contains(authUser)) {
            return new ErrorResult("Client has no permission on Thesis");
        }

        try {
            thesisRepo.delete(thesis);
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("Thesis deleted: " + thesis.getThesisNo());
    }

    @Override
    public Result addSupervisorToThesis(int thesisId, int userId) {
        return updateSupervisorAssociation(thesisId, userId, true, false);
    }

    @Override
    public Result removeSupervisorFromThesis(int thesisId, int userId) {
        return updateSupervisorAssociation(thesisId, userId, false, false);
    }

    @Override
    public Result addSupervisorToThesisByOwner(int thesisId, int userId) {
        return updateSupervisorAssociation(thesisId, userId, true, true);
    }

    @Override
    public Result removeSupervisorFromThesisByOwner(int thesisId, int userId) {
        return updateSupervisorAssociation(thesisId, userId, false, true);
    }

    private Result updateSupervisorAssociation(int thesisId, int userId, boolean addSupervisor, boolean checkOwnership) {
        DataResult thesisResult = getThesisById(thesisId);
        DataResult userResult = userService.getUserById(userId);

        if (!thesisResult.isSuccess()) {
            return new ErrorResult(thesisResult.getMessage());
        }
        if (!userResult.isSuccess()) {
            return new ErrorResult(userResult.getMessage());
        }

        Thesis thesis = (Thesis) thesisResult.getData();
        User supervisor = (User) userResult.getData();

        User author = userService.getUserByEmail(
                SecurityContextHolder.getContext().
                        getAuthentication().getName()).getData();

        if (checkOwnership && thesis.getAuthor() != author) {
            if (author == supervisor) {
                return new ErrorResult("Thesis Author can not be Supervisor");
            }
            return new ErrorResult("Client has no permission on Thesis");
        }

        boolean isExist = thesis.getSupervisors().contains(supervisor);
        if (isExist == addSupervisor) {
            String existError = isExist ? "User already supervise on Thesis" : "Thesis not supervised by User";
            return new ErrorResult(existError + ": " + supervisor.getEmail());
        }

        if (addSupervisor) {
            thesis.getSupervisors().add(supervisor);
            supervisor.getThesis().add(thesis);
        } else {
            thesis.getSupervisors().remove(supervisor);
            supervisor.getThesis().remove(thesis);
        }

        thesisRepo.save(thesis);
        userService.saveUser(supervisor);
        String action = addSupervisor ? "added to" : "removed from";
        return new SuccessResult("Supervisor " + action + " Thesis");

    }

}
