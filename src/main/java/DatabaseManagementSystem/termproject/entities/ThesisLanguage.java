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
@Table(name = "thesis_language")
public class ThesisLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thesis_language_id")
    private int thesisLanguageId;

    @Column(name = "thesis_language", unique = true)
    private String thesisLanguage;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "language")
    @JsonIgnore
    private List<Thesis> thesisList;

}
