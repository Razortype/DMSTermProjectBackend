package DatabaseManagementSystem.termproject.api.controller;

import DatabaseManagementSystem.termproject.business.abstracts.ThesisLanguageService;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.entities.ThesisLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/thesis-language")
@RequiredArgsConstructor
public class ThesisLanguageController {

    private final ThesisLanguageService thesisLanguageService;

    @GetMapping("")
    public ResponseEntity<DataResult<List<ThesisLanguage>>> getAllThesisLanguages() {
        DataResult result = thesisLanguageService.getAllThesisLanguages();
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

}
