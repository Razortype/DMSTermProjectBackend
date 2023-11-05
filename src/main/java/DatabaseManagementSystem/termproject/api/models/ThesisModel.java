package DatabaseManagementSystem.termproject.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThesisModel {

    @JsonProperty("thesis_no")
    private String thesisNo;

    private String title;

    @JsonProperty("abstract")
    private String thesisAbstract;

    private int year;
    private String university;
    private String institute;

    @JsonProperty("number_of_pages")
    private int numberOfPages;

    @JsonProperty("related_keywords")
    private String relatedKeywords;

    @Column(name = "type_id")
    private int typeId;

    @Column(name = "language_id")
    private int languageId;

    @Column(name = "supervisor_id")
    private int supervisorId;

    @Column(name = "co_supervisor_id")
    private int coSupervisorId;

}
