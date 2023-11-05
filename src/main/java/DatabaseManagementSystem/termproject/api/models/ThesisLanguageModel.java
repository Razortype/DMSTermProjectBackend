package DatabaseManagementSystem.termproject.api.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThesisLanguageModel {

    @Column(name = "language_name")
    private String languageName;

}
