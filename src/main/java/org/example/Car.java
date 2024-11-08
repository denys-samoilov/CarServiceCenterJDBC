package org.example;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")

public class Car {
    public Car (String model, Brand brand, int year) {
        this.brandId = brand;
        this.model = model;
        this.year = year;
    }

    public Car(){}



    public Brand getBrand() {
        return Brand.values()[id];
    }

    public void setBrand(Brand brand) {
        this.brandId = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public enum Brand{
        Volkswagen, BMW, Opel, Mercedes, Honda, Toyota, Nissan, Subaru, Chevrolet, Ford, Dodge
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Brand brandId;
    private String model;
    private int year;

}
