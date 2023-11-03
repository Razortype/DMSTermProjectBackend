package DatabaseManagementSystem.termproject.entities;

import DatabaseManagementSystem.termproject.core.enums.TextLanguage;
import DatabaseManagementSystem.termproject.core.enums.ThesisType;
import DatabaseManagementSystem.termproject.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thesis")
@EntityListeners(AuditingEntityListener.class)
public class Thesis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thesis_id")
    private int thesisId;

    @Column(name = "thesis_no", length = 7, unique = true)
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

    @Enumerated(EnumType.STRING)
    private ThesisType type;

    @Enumerated(EnumType.STRING)
    private TextLanguage language;

    @CreationTimestamp
    @Column(name = "submission_date")
    private Date submissionDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "supervisor_thesis_rel",
            joinColumns = @JoinColumn(name = "thesis_id", referencedColumnName = "thesis_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    )
    @Column(name = "supervisors")
    @JsonManagedReference
    private List<User> supervisors;

}
