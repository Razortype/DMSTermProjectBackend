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
public class ProfessionModel {

    @Column(name = "profession_name")
    private String professionName;

    @Column(name = "profession_description")
    private String professionDescription;

}
