package DatabaseManagementSystem.termproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "related_keyword")
public class RelatedKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private int keywordId;

    @Column(name = "related_keyword", unique = true, length = 100)
    private String relatedKeyword;

    @ManyToMany(mappedBy = "relatedKeywords")
    @JsonBackReference
    private List<Thesis> thesisList;

}
