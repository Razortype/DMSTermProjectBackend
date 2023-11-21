package DatabaseManagementSystem.termproject.api.controller;

import DatabaseManagementSystem.termproject.api.models.*;
import DatabaseManagementSystem.termproject.business.abstracts.*;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final UserService userService;
    private final ThesisService thesisService;
    private final ProfessionService professionService;
    private final ThesisTypeService thesisTypeService;
    private final ThesisLanguageService thesisLanguageService;
    private final InstituteService instituteService;
    private final UniversityService universityService;
    private final RelatedKeywordService relatedKeywordService;
    private final SubjectService subjectService;


    //////  User Manager Controller  //////

    ////////////////////////////////////


    ////// Thesis Manager Controller //////
    @DeleteMapping("/thesis/{thesis_id}")
    public ResponseEntity<Result> deleteThesisById(
            @PathVariable(name="thesis_id") int thesisId
    ) {
        Result result = thesisService.deleteById(thesisId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    /*
    @PutMapping("/thesis/{thesis_id}/add/{user_id}")
    public ResponseEntity<Result> addSupervisorToThesis(
            @PathVariable(name="thesis_id") int thesisId,
            @PathVariable(name="user_id") int userId
    ) {
        Result result = thesisService.addSupervisorToThesis(thesisId, userId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/thesis/{thesis_id}/remove/{user_id}")
    public ResponseEntity<Result> removeSupervisorFromThesis(
            @PathVariable(name="thesis_id") int thesisId,
            @PathVariable(name="user_id") int userId
    ) {
        Result result = thesisService.removeSupervisorFromThesis(thesisId, userId);
        if(!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
     */

    ////////////////////////////////////

    //////  Enum Class Controller  //////

    @PostMapping("/profession")
    public ResponseEntity<Result> addNewProfession(@RequestBody ProfessionModel model) {
        Result result = professionService.saveNewProfession(model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/profession/{profession-id}")
    public ResponseEntity<Result> deleteProfessionById(@PathVariable(name = "profession-id") int professionId) {
        Result result = professionService.deleteProfessionById(professionId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/profession/{profession-id}")
    public ResponseEntity<Result> updateProfessionById(@PathVariable(name = "profession-id") int professionId, @RequestBody ProfessionModel model) {
        Result result = professionService.updateProfessionById(professionId, model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/thesis-type")
    public ResponseEntity<Result> addNewThesisType(@RequestBody ThesisTypeModel model) {
        Result result = thesisTypeService.saveNewThesisType(model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/thesis-type/{thesis-type-id}")
    public ResponseEntity<Result> deleteThesisTypeById(@PathVariable(name = "thesis-type-id") int thesisTypeId) {
        Result result = thesisTypeService.deleteThesisType(thesisTypeId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/thesis-type/{thesis-type-id}")
    public ResponseEntity<Result> updateThesisTypeById(@PathVariable(name = "thesis-type-id") int thesisTypeId, @RequestBody ThesisTypeModel model) {
        Result result = thesisTypeService.updateThesisType(thesisTypeId, model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/thesis-language")
    public ResponseEntity<Result> addNewThesisLanguage(@RequestBody ThesisLanguageModel model) {
        Result result = thesisLanguageService.saveNewThesisLanguage(model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/thesis-language/{thesis-language-id}")
    public ResponseEntity<Result> deleteThesisLanguageById(@PathVariable(name = "thesis-language-id") int thesisLanguageId) {
        Result result = thesisLanguageService.deleteThesisLanguage(thesisLanguageId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/thesis-language/{thesis-language-id}")
    public ResponseEntity<Result> updateThesisLanguageById(@PathVariable(name = "thesis-language-id") int thesisLanguageId, @RequestBody ThesisLanguageModel model) {
        Result result = thesisLanguageService.updateThesisLanguage(thesisLanguageId, model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/institute")
    public ResponseEntity<Result> saveInstitute(@RequestBody InstituteModel model) {
        Result result = instituteService.saveInstitute(model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/institute/{institute-id}")
    public ResponseEntity<Result> deleteInstituteById(@PathVariable(name = "institute-id") int id) {
        Result result = instituteService.deleteInstituteById(id);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/institute/{institute-id}")
    public ResponseEntity<Result> updateInstituteById(@PathVariable(name = "institute-id") int id, @RequestBody InstituteModel model) {
        Result result = instituteService.updateInstituteById(id, model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/university")
    public ResponseEntity<Result> saveUniversity(@RequestBody UniversityModel model) {
        Result result = universityService.saveUniversity(model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/university/{university-id}")
    public ResponseEntity<Result> deleteUniversityById(@PathVariable(name = "university-id") int id) {
        Result result = universityService.deleteUniversityById(id);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/university/{university-id}")
    public ResponseEntity<Result> updateUniversityById(@PathVariable(name = "university-id") int id, @RequestBody UniversityModel model) {
        Result result = universityService.updateUniversityById(id, model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/related-keyword/{related-keyword-id}")
    public ResponseEntity<Result> deleteRelatedKeywordById(@PathVariable(name = "related-keyword-id") int id) {
        Result result = relatedKeywordService.deleteRelatedKeywordById(id);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/related-keyword/{related-keyword-id}")
    public ResponseEntity<Result> updateRelatedKeywordById(@PathVariable(name = "related-keyword-id") int id, @RequestBody RelatedKeywordModel model) {
        Result result = relatedKeywordService.updateRelatedKeywordById(id, model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/subject/{subject-id}")
    public ResponseEntity<Result> deleteSubjectById(@PathVariable(name = "subject-id") int id) {
        Result result = subjectService.deleteSubjectById(id);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/subject/{subject-id}")
    public ResponseEntity<Result> updateSubjectById(@PathVariable(name = "subject-id") int id, @RequestBody SubjectModel model) {
        Result result = subjectService.updateSubjectById(id, model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }


    ////////////////////////////////////

    @PutMapping("/thesis/{thesis-id}/add/keyword/{keyword-id}")
    public ResponseEntity<Result> addKeywordToThesis(@PathVariable(name = "thesis-id") int thesisId, @PathVariable(name = "keyword-id") int keywordId) {
        Result result = thesisService.addKeywordToThesis(thesisId, keywordId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/thesis/{thesis-id}/remove/keyword/{keyword-id}")
    public ResponseEntity<Result> removeKeywordFromThesis(@PathVariable(name = "thesis-id") int thesisId, @PathVariable(name = "keyword-id") int keywordId) {
        Result result = thesisService.removeKeywordFromThesis(thesisId, keywordId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/thesis/{thesis-id}/add/subject/{subject-id}")
    public ResponseEntity<Result> addSubjectToThesis(@PathVariable(name = "thesis-id") int thesisId, @PathVariable(name = "subject-id") int subjectId) {
        Result result = thesisService.addSubjectToThesis(thesisId, subjectId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/thesis/{thesis-id}/remove/subject/{subject-id}")
    public ResponseEntity<Result> removeSubjectFromThesis(@PathVariable(name = "thesis-id") int thesisId, @PathVariable(name = "subject-id") int subjectId) {
        Result result = thesisService.removeSubjectFromThesis(thesisId, subjectId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    ////////////////////////////////////
}
