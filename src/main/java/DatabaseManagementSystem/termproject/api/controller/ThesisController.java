package DatabaseManagementSystem.termproject.api.controller;

import DatabaseManagementSystem.termproject.api.models.ThesisModel;
import DatabaseManagementSystem.termproject.business.abstracts.ThesisService;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.Thesis;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/thesis")
@RequiredArgsConstructor
public class ThesisController {

    private final ThesisService thesisService;

    @GetMapping("")
    public ResponseEntity<DataResult<List<Thesis>>> getAllThesisByFiltered(
            @RequestParam(name = "word", required = false) String word,
            @RequestParam(name = "keywords", required = false) List<Integer> keywordIds,
            @RequestParam(name = "subjects", required = false) List<Integer> subjectIds,
            @RequestParam(name = "universities", required = false) List<Integer> universityIds,
            @RequestParam(name = "institutes", required = false) List<Integer> instituteIds,
            @RequestParam(name = "users", required = false) List<Integer> userIds,
            @RequestParam(name = "languages", required = false) List<Integer> languageIds,
            @RequestParam(name = "types", required = false) List<Integer> typeIds
    ) {
        DataResult result = thesisService.getThesisBySearchQuery(word, keywordIds, subjectIds, universityIds, instituteIds, userIds, languageIds, typeIds);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/my")
    public ResponseEntity<DataResult<List<Thesis>>> getAllThesisByUser() {
        DataResult result = thesisService.getAllThesisByUser();
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<Result> saveNewThesis(@RequestBody ThesisModel model) {
        Result result = thesisService.saveNewThesis(model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{thesis-id}")
    public ResponseEntity<Result> deleteThesisOwnByUser(@PathVariable(name = "thesis-id") int thesisId) {
        Result result = thesisService.deleteThesisOwnByUser(thesisId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{thesis-id}/add/keyword/{keyword-id}")
    public ResponseEntity<Result> addKeywordToThesis(@PathVariable(name = "thesis-id") int thesisId, @PathVariable(name = "keyword-id") int keywordId) {
        Result result = thesisService.addKeywordToThesisByOwner(thesisId, keywordId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{thesis-id}/remove/keyword/{keyword-id}")
    public ResponseEntity<Result> removeKeywordFromThesis(@PathVariable(name = "thesis-id") int thesisId, @PathVariable(name = "keyword-id") int keywordId) {
        Result result = thesisService.removeKeywordFromThesisByOwner(thesisId, keywordId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{thesis-id}/add/subject/{subject-id}")
    public ResponseEntity<Result> addSubjectToThesis(@PathVariable(name = "thesis-id") int thesisId, @PathVariable(name = "subject-id") int subjectId) {
        Result result = thesisService.addSubjectToThesisByOwner(thesisId, subjectId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{thesis-id}/remove/subject/{subject-id}")
    public ResponseEntity<Result> removeSubjectFromThesis(@PathVariable(name = "thesis-id") int thesisId, @PathVariable(name = "subject-id") int subjectId) {
        Result result = thesisService.removeSubjectFromThesisByOwner(thesisId, subjectId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }


    /*
    @PutMapping("/{thesis_id}/add/{user_id}")
    public ResponseEntity<Result> addSupervisorToThesis(
            @PathVariable(name="thesis_id") int thesisId,
            @PathVariable(name="user_id") int userId
    ) {
        Result result = thesisService.addSupervisorToThesisByOwner(thesisId, userId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{thesis_id}/remove/{user_id}")
    public ResponseEntity<Result> removeSupervisorFromThesis(
            @PathVariable(name="thesis_id") int thesisId,
            @PathVariable(name="user_id") int userId
    ) {
        Result result = thesisService.removeSupervisorFromThesisByOwner(thesisId, userId);
        if(!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
    */

}
