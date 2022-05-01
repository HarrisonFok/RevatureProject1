package com.project1.springbootproject1email.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Email")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    @Id
    //@Column(name = "emailID", columnDefinition = "AUTO_INCREMENT")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int emailID;

    @Column(name = "fromEmail")
    private String fromEmail;

    @Column(name = "toEmail")
    private String toEmail;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private Date date;
}
