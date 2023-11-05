package DatabaseManagementSystem.termproject.api.controller;

import DatabaseManagementSystem.termproject.business.abstracts.ThesisTypeService;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.entities.ThesisType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/thesis-type")
@RequiredArgsConstructor
public class ThesisTypesController {

    private final ThesisTypeService thesisTypeService;

    @GetMapping("")
    public ResponseEntity<DataResult<List<ThesisType>>> getAllThesisTypes() {
        DataResult result = thesisTypeService.getAllThesisTypes();
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

}
