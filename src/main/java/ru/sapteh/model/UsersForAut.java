package ru.sapteh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users_for_aut")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsersForAut {

    @Id
    private int id;
    private String login;
    private String pass;
    private String rule;

}
