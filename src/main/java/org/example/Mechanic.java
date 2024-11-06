package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mechanics")
public class Mechanic extends User {
    public Mechanic(String name, String login, String password, String phoneNumber)
    {
        super(name, login, password, phoneNumber);
    }

}
