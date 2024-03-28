package Stream.project.stream.repositories;

import Stream.project.stream.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long > {
    Optional<User> findByUserName(String userName);
    Optional<User> findByFirstName(String firstName);
    Optional<User> findByLastName(String lastName);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
}
