package org.example;

import java.time.LocalDate;

public class Order {
    private int orderID;
    private Customer customer;
    private Mechanic mechanic;
    private String description;
    private LocalDate date = LocalDate.now();
    private boolean isConfirmed = false;
    private float price = 0;

    public Order(){}

    public Order(int orderID, Customer customer, Mechanic mechanic, String description){
        this.orderID = orderID;
        this.customer = customer;
        this.mechanic = mechanic;
        this.description = description;
    }


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate() {
        this.date = LocalDate.now();
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setBoolean(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price){
        this.price = price;
    }
    @Override
    public String toString() {
        Car car = customer.getCar();
        var toString =  "ID замовлення: " + orderID + "\n" +
               "Логін замовника: " + customer.getLogin() + "\n" +
               "Ім'я замовника: " + customer.getName() + "\n" +
               "Номер телефону замовника: " + customer.getPhoneNumber() + "\n" +
               "Машина замовника: " + car.getBrand() + " " + car.getSeries() + " " + car.getYear() + "\n" +
               "Ім'я механіка: " + mechanic.getName() + "\n" +
               "Номер телефону механіка: " + mechanic.getPhoneNumber() + "\n" +
               "Опис замовлення: " + description + "\n" +
               "Дата замовлення: " + date + "\n" +
               "Чи підтверджено замовлення: " + isConfirmed + "\n" +
               "Ціна замовлення: " + price + "\n";
        return toString;
}
}
