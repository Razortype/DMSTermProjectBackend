package DatabaseManagementSystem.termproject.entities;

import DatabaseManagementSystem.termproject.user.User;
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
@Table(name = "profession")
public class Profession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profession_id")
    private int professionId;

    @Column(name = "profession_name")
    private String professionName;

    @Column(name = "profession_description", length = 100)
    private String professionDescription;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "profession")
    @JsonIgnore
    private List<User> users;

}
