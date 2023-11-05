package DatabaseManagementSystem.termproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thesis_type")
public class ThesisType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thesis_type_id")
    private int thesisTypeId;

    @Column(name = "thesis_type_name", unique = true)
    private String thesisTypeName;

    @Column(name = "thesis_type_description")
    private String thesisTypeDescription;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "type")
    @JsonIgnore
    private List<Thesis> thesisList;

}
