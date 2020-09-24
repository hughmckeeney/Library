package ibtwlb.ibtwlb.models;

import javax.persistence.*;

@Entity
@Table(name="artifacts")
public class Artifact{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String author;
    @Column
    private String description;
    @Column
    private String type;
    @Column 
    private Boolean onLoan = false;
    
  
    public Artifact() {
    }
    public Artifact(String name, String author, String description, String type)
    {
        this.name = name;
        this.author = author;
        this.description = description;
        this.type = type;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getAuthor()
    {
        return this.author;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return this.type;
    }

    public Boolean getOnLoan()
    {
        return this.onLoan;
    }
    public void setOnLoan(Boolean onLoan)
    {
        this.onLoan=onLoan;
    }
}

