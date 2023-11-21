package DatabaseManagementSystem.termproject.api.controller;

import DatabaseManagementSystem.termproject.api.models.RelatedKeywordModel;
import DatabaseManagementSystem.termproject.business.abstracts.RelatedKeywordService;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.RelatedKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/related-keyword")
@RequiredArgsConstructor
public class RelatedKeywordController {

    private final RelatedKeywordService relatedKeywordService;

    @GetMapping("")
    public ResponseEntity<DataResult<List<RelatedKeyword>>> getAllRelatedKeyword() {
        DataResult result = relatedKeywordService.getAllRelatedKeyword();
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{related-keyword-id}")
    public ResponseEntity<DataResult<RelatedKeyword>> getRelatedKeywordById(@PathVariable(name = "related-keyword-id") int id) {
        DataResult result = relatedKeywordService.getRelatedKeywordById(id);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<Result> saveRelatedKeyword(@RequestBody RelatedKeywordModel model) {
        Result result = relatedKeywordService.saveRelatedKeyword(model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

}
