package ibtwlb.ibtwlb.models;

import javax.persistence.*;

import ibtwlb.ibtwlb.repositories.ArtifactsRepository;

@Entity
@Table(name = "loan")
public class Loan{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId;
    @Column
    private int artifactId;
    @Column
    private String loanStartDate;
    @Column
    private String loanEndDate;
    @Column
    private String artifactName;

    public Loan() {
    }

    public Loan(Long user_id, int artifact_id, String loanStartDate, String loanEndDate, ArtifactsRepository artifactsRepository) {
        this.userId = user_id;
        this.artifactId = artifact_id;
        this.loanStartDate = loanStartDate;
        this.loanEndDate = loanEndDate;
        Artifact artifact=artifactsRepository.findById(artifact_id);
        artifact.setOnLoan(true);
        artifactsRepository.save(artifact);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long user_id) {
        this.userId = user_id;
    }

    public int getArtifactId() {
        return this.artifactId;
    }

    public void setArtifactId(int artifact_id) {
        this.artifactId = artifact_id;
    }

    public String getLoanStartDate() {
        return this.loanStartDate;
    }

    public void setLoanStartDate(String loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public String getLoanEndDate() {
        return this.loanEndDate;
    }

    public void setLoanEndDate(String loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", user_id='" + getUserId() + "'" + ", artifact_id='" + getArtifactId()
                + "'" + ", loanStartDate='" + getLoanStartDate() + "'" + ", loanEndDate='" + getLoanEndDate() + "'"
                + "}";
    }

    public void setArtifactName(String name) {
        this.artifactName = name;
    }

    public String getArtifactName() {
        return this.artifactName;
    }

}