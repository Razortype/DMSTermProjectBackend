package DatabaseManagementSystem.termproject.api.controller;

import DatabaseManagementSystem.termproject.business.abstracts.InstituteService;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.entities.Institute;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/institute")
@RequiredArgsConstructor
public class InstituteController {

    private final InstituteService instituteService;

    @GetMapping("")
    public ResponseEntity<DataResult<List<Institute>>> getAllInstitute() {
        DataResult result = instituteService.getAllInstitute();
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{institute-id}")
    public ResponseEntity<DataResult<Institute>> getInstituteById(@PathVariable(name = "institute-id") int id) {
        DataResult result = instituteService.getInstituteById(id);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

}
