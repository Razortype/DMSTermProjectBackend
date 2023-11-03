package DatabaseManagementSystem.termproject.core.utils.results;

import DatabaseManagementSystem.termproject.core.utils.helpers.TimeService;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Result {

    private boolean success;
    private String message;
    private final ZonedDateTime executedAt;

    public Result(boolean success) {
        this.success = success;
        this.executedAt = TimeService.getCurrentTime();
    }

    public Result(boolean success, String message) {
        this(success);
        this.message = message;
    }

}
