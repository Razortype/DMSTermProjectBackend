package DatabaseManagementSystem.termproject.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThesisModel {

    @JsonProperty("thesis_no")
    private String thesisNo;

    @JsonProperty("subject_ids")
    private Integer[] subjectIds;

    private String title;

    @JsonProperty("abstract")
    private String thesisAbstract;

    private int year;

    @JsonProperty("university_id")
    private int universityId;

    @JsonProperty("institute_id")
    private int instituteId;

    @JsonProperty("number_of_pages")
    private int numberOfPages;

    @JsonProperty("related_keyword_ids")
    private Integer[] relatedKeywordIds;

    @Column(name = "type_id")
    private int typeId;

    @Column(name = "language_id")
    private int languageId;

    @Column(name = "supervisor_id")
    private int supervisorId;

    @Column(name = "co_supervisor_id")
    private int coSupervisorId;

}
