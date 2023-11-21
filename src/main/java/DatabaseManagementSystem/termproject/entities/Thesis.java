package DatabaseManagementSystem.termproject.entities;

import DatabaseManagementSystem.termproject.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.validation.constraints.NotNull;
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "thesis_subject_rel",
            joinColumns = @JoinColumn(name = "thesis_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "thesis_keyword_rel",
            joinColumns = @JoinColumn(name = "thesis_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    @Column(name = "related_keywords")
    private List<RelatedKeyword> relatedKeywords;

    @Column(length = 500)
    private String title;

    @Column(name = "abstract", length = 5000)
    private String thesisAbstract;

    private int year;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @Column(name = "number_of_pages")
    private int numberOfPages;

    @ManyToOne
    @JoinColumn(name = "thesis_type_id")
    private ThesisType type;

    @ManyToOne
    @JoinColumn(name = "thesis_language_id")
    private ThesisLanguage language;

    @CreationTimestamp
    @Column(name = "submission_date")
    private Date submissionDate;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "user_id")
    @NotNull
    private User author;

    @ManyToOne
    @JoinColumn(name = "supervisor_id", referencedColumnName = "user_id")
    @NotNull
    private User supervisor;

    @ManyToOne
    @JoinColumn(name = "co_supervisor_id", referencedColumnName = "user_id")
    @NotNull
    private User coSupervisor;

}
