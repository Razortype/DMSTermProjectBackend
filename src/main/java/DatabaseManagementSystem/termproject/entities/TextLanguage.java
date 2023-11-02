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
@Table(name = "text_language")
public class TextLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "language_id")
    private int languageId;

    private String language;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "language")
    @JsonIgnore
    private List<Thesis> thesisList;

}
