package ibtwlb.ibtwlb.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ibtwlb.ibtwlb.models.Artifact;

@Repository
public interface ArtifactsRepository extends JpaRepository<Artifact, Integer>{
    List<Artifact> findAll();
    Artifact findById(int id);
    List<Artifact> findByName(String name);
    List<Artifact> findByAuthor(String author);
    List<Artifact> findByType(String type);
    List<Artifact> findByNameContainingIgnoreCase(String name);
    List<Artifact> findByDescriptionContainingIgnoreCase(String description);
    List<Artifact> findByAuthorContainingIgnoreCase(String author);
    List<Artifact> deleteById(int id);

}
