package DatabaseManagementSystem.termproject.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @JsonProperty("birth_year")
    private int birthYear;
    private String gender;
    @JsonProperty("profession_id")
    private int professionId;

}
