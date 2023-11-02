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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "type_id")
    private int thesisTypeId;

    @Column(name = "type_name")
    private String thesisTypeName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "type")
    @JsonIgnore
    private List<Thesis> thesisList;

}
