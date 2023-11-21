package DatabaseManagementSystem.termproject.api.controller;

import DatabaseManagementSystem.termproject.api.models.SubjectModel;
import DatabaseManagementSystem.termproject.business.abstracts.SubjectService;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.core.utils.results.Result;
import DatabaseManagementSystem.termproject.entities.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping("")
    public ResponseEntity<DataResult<List<Subject>>> getAllSubject() {
        DataResult result = subjectService.getAllSubject();
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{subject-id}")
    public ResponseEntity<DataResult<Subject>> getSubjectById(@PathVariable(name = "subject-id") int id) {
        DataResult result = subjectService.getSubjectById(id);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<Result> saveSubject(@RequestBody SubjectModel model) {
        Result result = subjectService.saveSubject(model);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

}
