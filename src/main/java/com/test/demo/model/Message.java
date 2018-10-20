package com.test.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long idMessage;

    private String message;

    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties("messages")
    private User user;


}
