package ibtwlb.ibtwlb.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ibtwlb.ibtwlb.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    User findById(long id);
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findByNameContainingIgnoreCase(String name);
    List<User> findByUsernameContainingIgnoreCase(String username);
    List<User> findByEmailContainingIgnoreCase(String email);
    List<User> findByLocationContainingIgnoreCase(String location);

}