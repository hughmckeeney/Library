package ibtwlb.ibtwlb.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="user")
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Transient
    private String passwordConfirm;
  
    @Column
    private String bio;
    @Column
    private String location;
    @Column
    private String role;


    public User() {}
    public User(String name, String username, String email, String password, String passwordConfirm, String bio, String location, String role)
    {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirm = password;
        this.bio = bio;
        this.location = location;
        this.role = role;
    }

    //this constructor is for the sign up form - it does not include 'role' - signups default to USER
    public User(String name, String username, String email, String password, String passwordConfirm, String bio, String location)
    {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirm = password;
        this.bio = bio;
        this.location = location;
        this.role = "USER";
    }

    //used for updating user information (edit profile)
    public User(String bio, String email, String location)
    {
        this.bio = bio;
        this.email = email;
        this.location = location;
    }

    public void setId(Long id)
    {
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

    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public String getBio()
    {
        return this.bio;
    }


    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getLocation()
    {
        return this.location;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "[" + this.username + ", " +
        this.getEmail() + ", " +
        this.getBio() + ", " +
        this.getLocation() + ", " +
        this.getRole() + "]";
    }



}