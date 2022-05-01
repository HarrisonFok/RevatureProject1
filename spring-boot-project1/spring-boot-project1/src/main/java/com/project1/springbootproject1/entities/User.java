package com.project1.springbootproject1.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "User")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    //@Column(name = "userID", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public boolean isEmployee() {
        return this.getRole().equalsIgnoreCase("employee");
    }

    public boolean isManager() {
        return this.getRole().equalsIgnoreCase("manager");
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userID")
    private List<ReimbursementRequest> reimbursementRequestList;
}
