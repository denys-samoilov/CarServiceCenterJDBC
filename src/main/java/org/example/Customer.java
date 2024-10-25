package org.example;

public class Customer extends User {

    public Customer(int id, String name, String login, String password, String phoneNumber, Car car)
    {
        super(id, name, login, password, phoneNumber);
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
