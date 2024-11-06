package org.example;


import jakarta.persistence.*;

@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer extends User {

    public Customer(String name, String login, String password, String phoneNumber, Car car)
    {
        super(name, login, password, phoneNumber);
        this.car = car;
    }

    public Customer(){};


    private Car car;
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
