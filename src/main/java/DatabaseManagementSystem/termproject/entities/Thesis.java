package DatabaseManagementSystem.termproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thesis")
@EntityListeners(AuditingEntityListener.class)
public class Thesis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "thesis_id")
    private int thesisId;

    @Column(name = "thesis_no", length = 7)
    private String thesisNo;

    @Column(length = 500)
    private String title;

    @Column(name = "abstract", length = 5000)
    private String thesisAbstract;

    private int year;

    private String university;

    private String institute;

    @Column(name = "number_of_pages")
    private int numberOfPages;

    @Column(name = "related_keywords")
    private String relatedKeywords;

    @CreationTimestamp
    @Column(name = "submission_date")
    private Date submissionDate;

    @ManyToOne
    @JoinTable(name = "type_id")
    private ThesisType type;

    @ManyToOne
    @JoinTable(name = "language_id")
    private TextLanguage language;

}
