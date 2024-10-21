package Stream.project.stream.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(cascade = CascadeType.ALL ,fetch = FetchType.LAZY ,mappedBy = "user",orphanRemoval = true)
    @JsonManagedReference
    private List<Address> addresses;
    @OneToMany(cascade = CascadeType.ALL ,fetch = FetchType.LAZY,mappedBy = "user",orphanRemoval = true)
    private Set<Role> role;


}
