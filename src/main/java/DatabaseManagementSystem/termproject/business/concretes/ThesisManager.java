package DatabaseManagementSystem.termproject.business.concretes;

import DatabaseManagementSystem.termproject.api.models.ThesisModel;
import DatabaseManagementSystem.termproject.business.abstracts.ThesisLanguageService;
import DatabaseManagementSystem.termproject.business.abstracts.ThesisService;
import DatabaseManagementSystem.termproject.business.abstracts.ThesisTypeService;
import DatabaseManagementSystem.termproject.business.abstracts.UserService;
import DatabaseManagementSystem.termproject.core.utils.results.*;
import DatabaseManagementSystem.termproject.dataAccess.ThesisRepository;
import DatabaseManagementSystem.termproject.entities.Thesis;
import DatabaseManagementSystem.termproject.entities.ThesisLanguage;
import DatabaseManagementSystem.termproject.entities.ThesisType;
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
    private final ThesisTypeService thesisTypeService;
    private final ThesisLanguageService thesisLanguageService;

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
        DataResult typeResult = thesisTypeService.getById(model.getTypeId());
        DataResult languageResult = thesisLanguageService.getById(model.getLanguageId());
        DataResult supervisorResult = userService.getUserById(model.getSupervisorId());
        DataResult coSupervisorResult = userService.getUserById(model.getCoSupervisorId());

        if (!typeResult.isSuccess()) {
            return new ErrorResult(typeResult.getMessage());
        }
        if (!languageResult.isSuccess()) {
            return new ErrorResult(languageResult.getMessage());
        }
        if (!supervisorResult.isSuccess()) {
            return new ErrorResult("Supervisor: " + supervisorResult.getMessage());
        }
        if (!coSupervisorResult.isSuccess()) {
            return new ErrorResult("Co-Supervisor: " + coSupervisorResult.getMessage());
        }

        DataResult userResult = userService.getUserByEmail(
                SecurityContextHolder.getContext().
                        getAuthentication().getName());

        User author = (User) userResult.getData();
        User supervisor = (User) supervisorResult.getData();
        User coSupervisor = (User) coSupervisorResult.getData();

        if (author == supervisor || author == coSupervisor || supervisor == coSupervisor) {
            return new ErrorResult("Author or Supervisor or Co-Supervisor same error: " +
                    author.getUserId() + "-" +
                    supervisor.getUserId() + "-" +
                    coSupervisor.getUserId());
        }

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
                            .type((ThesisType) typeResult.getData())
                            .language((ThesisLanguage) languageResult.getData())
                            .author((User) userResult.getData())
                            .supervisor((User) supervisorResult.getData())
                            .coSupervisor((User) coSupervisorResult.getData())
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

        if (thesis.getAuthor().getEmail() != authUser.getEmail()) { // || thesis.getSupervisors().contains(authUser)
            return new ErrorResult("Client has no permission on Thesis");
        }

        try {
            thesisRepo.delete(thesis);
        } catch (Exception e) {
            return new ErrorResult("Unexpected Error Occurred: " + e.getMessage());
        }
        return new SuccessResult("Thesis deleted: " + thesis.getThesisNo());
    }
    /*

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

     */

}
