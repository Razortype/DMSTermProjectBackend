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
    public ResponseEntity<DataResult<List<Thesis>>> getAllThesis() {
        DataResult result = thesisService.getAllThesis();
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

    @DeleteMapping("/{thesis_id}")
    public ResponseEntity<Result> deleteThesisOwnByUser(@PathVariable(name = "thesis_id") int thesisId) {
        Result result = thesisService.deleteThesisOwnByUser(thesisId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

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

}
