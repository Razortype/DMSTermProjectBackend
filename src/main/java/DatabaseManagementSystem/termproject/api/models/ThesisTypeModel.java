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
public class ThesisTypeModel {

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "type_description")
    private String typeDescription;

}
