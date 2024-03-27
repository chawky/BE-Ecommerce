package Stream.project.stream.models;

import lombok.*;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

import javax.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EnableJdbcAuditing
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String userName;
    @Column
    private String password;
}
