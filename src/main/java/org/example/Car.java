package org.example;

public class Car {
    public Car(int id, String series, int year, Brand brand) {
        this.id = id;
        this.brand = brand;
        this.series = series;
        this.year = year;
    }

    public Car(){}



    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
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
    private int id;
    private Brand brand;
    private String series;
    private int year;

}
