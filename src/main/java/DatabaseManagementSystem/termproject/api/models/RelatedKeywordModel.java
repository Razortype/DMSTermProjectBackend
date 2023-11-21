package DatabaseManagementSystem.termproject.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelatedKeywordModel {

    @JsonProperty("related_keyword")
    private String relatedKeyword;

}
