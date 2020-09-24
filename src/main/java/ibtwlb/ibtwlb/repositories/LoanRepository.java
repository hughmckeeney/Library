package ibtwlb.ibtwlb.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ibtwlb.ibtwlb.models.Loan;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findAll();
    Loan findById(long id);
    List<Loan> findByUserId(Long user_id);
    List<Loan> findByArtifactId(int artifact_id);
}