package org.example;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends User {

    public Customer(String name, String login, String password, String phoneNumber, Car car)
    {
        super(name, login, password, phoneNumber);
        this.car = car;
    }

    public Customer(){};

    @OneToOne
    private Car car;
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
