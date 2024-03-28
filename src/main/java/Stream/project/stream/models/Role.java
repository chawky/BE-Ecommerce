package Stream.project.stream.models;

import Stream.project.stream.ERole;
import lombok.*;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column
    private ERole roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
