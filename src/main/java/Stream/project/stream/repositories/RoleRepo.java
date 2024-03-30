package Stream.project.stream.repositories;


import Stream.project.stream.models.ERole;
import Stream.project.stream.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole name);

}
