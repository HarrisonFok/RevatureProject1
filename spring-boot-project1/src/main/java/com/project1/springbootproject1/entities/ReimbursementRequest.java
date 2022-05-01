package com.project1.springbootproject1.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ReimbursementRequest")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReimbursementRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // @Column(name = "reimbursementID", columnDefinition = "AUTO_INCREMENT")
    private int reimbursementID;

    @Column(name = "dateOfPurchase")
    private Date dateOfPurchase;

    @Column(name = "description")
    private String description;

    @Column(name = "total")
    private int total;

    @Column(name = "status")
    private String status = "pending";

    @Column(name = "userID")
    private int userID;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "reimbursementId")
    private List<Item> items;

    public boolean isValidAction(String action) {
        return action.equalsIgnoreCase("approve") || action.equalsIgnoreCase("decline") || action.equalsIgnoreCase("reassign");
    }
}
