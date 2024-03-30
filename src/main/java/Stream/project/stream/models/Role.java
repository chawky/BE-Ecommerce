package Stream.project.stream.models;

import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column
    private ERole roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Role(ERole eRole, User user) {
        this.roleName = eRole;
        this.user = user;
    }
}
