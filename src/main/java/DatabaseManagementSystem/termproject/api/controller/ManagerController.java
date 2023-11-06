package DatabaseManagementSystem.termproject.api.controller;

import DatabaseManagementSystem.termproject.api.models.ProfessionModel;
import DatabaseManagementSystem.termproject.api.models.ThesisLanguageModel;
import DatabaseManagementSystem.termproject.api.models.ThesisTypeModel;
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

    ////////////////////////////////////

}
