package Stream.project.stream.repositories;

import Stream.project.stream.models.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData ,Long > {
}
