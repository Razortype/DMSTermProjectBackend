package DatabaseManagementSystem.termproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "institute")
public class Institute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "institute_id")
    private int instituteId;

    @Column(name = "institute_name", unique = true)
    private String instituteName;

}
