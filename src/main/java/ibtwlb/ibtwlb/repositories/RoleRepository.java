package ibtwlb.ibtwlb.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ibtwlb.ibtwlb.models.*;

@Repository
public interface RoleRepository extends JpaRepository<User, Long> {
    
}