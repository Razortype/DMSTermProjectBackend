package DatabaseManagementSystem.termproject.business.concretes;

import DatabaseManagementSystem.termproject.api.models.ThesisModel;
import DatabaseManagementSystem.termproject.business.abstracts.*;
import DatabaseManagementSystem.termproject.core.utils.results.*;
import DatabaseManagementSystem.termproject.dataAccess.ThesisRepository;
import DatabaseManagementSystem.termproject.entities.*;
import DatabaseManagementSystem.termproject.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThesisManager implements ThesisService {

    private final ThesisRepository thesisRepo;
    private final UserService userService;
    private final ThesisTypeService thesisTypeService;
    private final ThesisLanguageService thesisLanguageService;
    private final SubjectService subjectService;
    private final RelatedKeywordService relatedKeywordService;
    private final UniversityService universityService;
    private final InstituteService instituteService;

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
        DataResult universityResult = universityService.getUniversityById(model.getUniversityId());
        DataResult instituteResult = instituteService.getInstituteById(model.getInstituteId());
        DataResult relatedKeywordsResult = relatedKeywordService.getRelatedKeywordsByIdsIn(Arrays.stream(model.getRelatedKeywordIds()).toList());
        DataResult subjectResult = subjectService.getSubjectsByIdsIn(Arrays.stream(model.getSubjectIds()).toList());

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
        if (!universityResult.isSuccess()) {
            return new ErrorResult(universityResult.getMessage());
        }
        if (!instituteResult.isSuccess()) {
            return new ErrorResult(instituteResult.getMessage());
        }
        if (!relatedKeywordsResult.isSuccess()) {
            return new ErrorResult(relatedKeywordsResult.getMessage() + "(save)");
        }
        if (!subjectResult.isSuccess()) {
            return new ErrorResult(subjectResult.getMessage() + "(save)");
        }
        List<Subject> subjects = (List<Subject>) subjectResult.getData();
        if (subjects.size() == 0) {
            return new ErrorResult("At least one Subjects should provided");
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
                            .subjects(subjects)
                            .title(model.getTitle())
                            .thesisAbstract(model.getThesisAbstract())
                            .year(model.getYear())
                            .university((University) universityResult.getData())
                            .institute((Institute) instituteResult.getData())
                            .numberOfPages(model.getNumberOfPages())
                            .relatedKeywords((List<RelatedKeyword>) relatedKeywordsResult.getData())
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

    Result updateSubjectThesisAssociation(int thesisId, int subjectId, boolean addSubject, boolean checkOwnership) {
        DataResult thesisResult = getThesisById(thesisId);
        DataResult subjectResult = subjectService.getSubjectById(subjectId);
        if (!thesisResult.isSuccess()) {
            return new ErrorResult(thesisResult.getMessage());
        }
        if(!subjectResult.isSuccess()) {
            return new ErrorResult(subjectResult.getMessage());
        }
        Thesis thesis = (Thesis) thesisResult.getData();
        Subject subject = (Subject) subjectResult.getData();

        User author = userService.getUserByEmail(
                SecurityContextHolder.getContext().
                        getAuthentication().getName()).getData();

        if (checkOwnership && thesis.getAuthor() != author) {
            if (author == thesis.getAuthor()) {
                return new ErrorResult("Thesis Author can not be Supervisor");
            }
            return new ErrorResult("Client has no permission on Thesis");
        }

        if (addSubject == thesis.getSubjects().contains(subject)) {
            String status = addSubject ? "already" : "not";
            return new ErrorResult("Subject " + status + " exists in Thesis");
        }

        if (addSubject) {
            thesis.getSubjects().add(subject);
            subject.getThesisList().add(thesis);
        } else {
            thesis.getSubjects().remove(subject);
            subject.getThesisList().remove(thesis);
        }

        thesisRepo.save(thesis);
        subjectService.saveSubject(subject);

        String action = addSubject ? "added to" : "removed from";
        return new SuccessResult("Subject " + action + " Thesis");

    }

    Result updateKeywordThesisAssociation(int thesisId, int keywordId, boolean addKeyword, boolean checkOwnership) {
        DataResult thesisResult = getThesisById(thesisId);
        DataResult keywordResult = relatedKeywordService.getRelatedKeywordById(keywordId);
        if (!thesisResult.isSuccess()) {
            return new ErrorResult(thesisResult.getMessage());
        }
        if(!keywordResult.isSuccess()) {
            return new ErrorResult(keywordResult.getMessage());
        }
        Thesis thesis = (Thesis) thesisResult.getData();
        RelatedKeyword keyword = (RelatedKeyword) keywordResult.getData();

        User author = userService.getUserByEmail(
                SecurityContextHolder.getContext().
                        getAuthentication().getName()).getData();

        if (checkOwnership && thesis.getAuthor() != author) {
            if (author == thesis.getAuthor()) {
                return new ErrorResult("Thesis Author can not be Supervisor");
            }
            return new ErrorResult("Client has no permission on Thesis");
        }

        if (addKeyword == thesis.getRelatedKeywords().contains(keyword)) {
            String status = addKeyword ? "already" : "not";
            return new ErrorResult("Related keyword " + status + " exists in Thesis");
        }

        if (addKeyword) {
            thesis.getRelatedKeywords().add(keyword);
            keyword.getThesisList().add(thesis);
        } else {
            thesis.getRelatedKeywords().remove(keyword);
            keyword.getThesisList().remove(thesis);
        }

        thesisRepo.save(thesis);
        relatedKeywordService.saveRelatedKeyword(keyword);

        String action = addKeyword ? "added to" : "removed from";
        return new SuccessResult("Related Keyword " + action + " Thesis");

    }

    @Override
    public Result addSubjectToThesisByOwner(int thesisId, int subjectId) {
        return updateSubjectThesisAssociation(thesisId, subjectId, true, true);
    }

    @Override
    public Result removeSubjectFromThesisByOwner(int thesisId, int subjectId) {
        return updateSubjectThesisAssociation(thesisId, subjectId, false, true);
    }

    @Override
    public Result addKeywordToThesisByOwner(int thesisId, int keywordId) {
        return updateKeywordThesisAssociation(thesisId, keywordId, true, true);
    }

    @Override
    public Result removeKeywordFromThesisByOwner(int thesisId, int keywordId) {
        return updateKeywordThesisAssociation(thesisId, keywordId, false, true);
    }

    @Override
    public Result addSubjectToThesis(int thesisId, int subjectId) {
        return updateSubjectThesisAssociation(thesisId, subjectId, true, false);
    }

    @Override
    public Result removeSubjectFromThesis(int thesisId, int subjectId) {
        return updateSubjectThesisAssociation(thesisId, subjectId, false, false);
    }

    @Override
    public Result addKeywordToThesis(int thesisId, int keywordId) {
        return updateKeywordThesisAssociation(thesisId, keywordId, true, false);
    }

    @Override
    public Result removeKeywordFromThesis(int thesisId, int keywordId) {
        return updateKeywordThesisAssociation(thesisId, keywordId, false, false);
    }

    @Override
    public DataResult<List<Thesis>> getThesisBySearchQuery(String word, List<Integer> keywords, List<Integer> subjects, List<Integer> universities, List<Integer> institutes, List<Integer> users, List<Integer> languages, List<Integer> types) {
        List<Thesis> filteredThesisList = thesisRepo.findBySearchQuery(word, keywords, subjects, universities, institutes, users, languages, types);

        List<String> filtered = new ArrayList<>();
        if (word != null && !word.isEmpty()) filtered.add("word");
        if (keywords != null && !keywords.isEmpty()) filtered.add("keyword");
        if (subjects != null && !subjects.isEmpty()) filtered.add("subject");
        if (universities != null && !universities.isEmpty()) filtered.add("university");
        if (institutes != null && !institutes.isEmpty()) filtered.add("institute");
        if (users != null && !users.isEmpty()) filtered.add("users");
        if (languages != null && !languages.isEmpty()) filtered.add("language");
        if (types != null && !types.isEmpty()) filtered.add("type");

        String filteredText = "";
        if (!filtered.isEmpty()) filteredText = String.format(" | filtered : [ %s ]", String.join(", ", filtered));

        return new SuccessDataResult<>(filteredThesisList, "Thesis Result" + filteredText);
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
