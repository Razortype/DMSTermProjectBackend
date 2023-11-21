package DatabaseManagementSystem.termproject.api.controller;

import DatabaseManagementSystem.termproject.business.abstracts.UniversityService;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.entities.University;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/university")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping("")
    public ResponseEntity<DataResult<List<University>>> getAllUniversity() {
        DataResult result = universityService.getAllUniversity();
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{university-id}")
    public ResponseEntity<DataResult<University>> getUniversityById(@PathVariable(name = "university-id") int id) {
        DataResult result = universityService.getUniversityById(id);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

}
