package DatabaseManagementSystem.termproject.api.controller;

import DatabaseManagementSystem.termproject.business.abstracts.UserService;
import DatabaseManagementSystem.termproject.core.utils.results.DataResult;
import DatabaseManagementSystem.termproject.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
    @GetMapping("")
    public ResponseEntity<DataResult<Integer>> getAllThesisByFiltered(
            @RequestParam(name = "word", required = false) String word,
            @RequestParam(name = "keywords", required = false) List<Integer> keywordIds,
            @RequestParam(name = "subjects", required = false) List<Integer> subjectIds,
            @RequestParam(name = "universities", required = false) List<Integer> universityIds,
            @RequestParam(name = "institutes", required = false) List<Integer> instituteIds,
            @RequestParam(name = "users", required = false) List<Integer> userIds,
            @RequestParam(name = "languages", required = false) List<Integer> languageIds,
            @RequestParam(name = "types", required = false) List<Integer> typeIds,
            @RequestParam(name = "random", required = false) Integer n
    )
     */

    @GetMapping("")
    public ResponseEntity<DataResult<List<User>>> getAllUsers(
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "random", required = false) Integer n
    ) {

        DataResult result;
        if (n != null) {
            result = userService.getNRandomUser(n);
        } else if (text != null) {
            result = userService.searchUserByFilter(text);
        } else {
            result = userService.getAllUser();
        }

        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/current")
    public ResponseEntity<DataResult<User>> getCurrentUserByToken() {
        DataResult result = userService.getUserByToken();
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<DataResult<User>> getUserById(@PathVariable(name = "user_id") int userId) {
        DataResult result = userService.getUserById(userId);
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

}
