package org.example;

<<<<<<< Updated upstream
=======
import jakarta.persistence.*;

@Entity
@Table(name = "cars")
>>>>>>> Stashed changes
public class Car {
    public Car(String series, int year, BrandId brand) {
        this.id = id;
        this.brandId = brand;
        this.model = series;
        this.year = year;
    }

    public Car(){}



    public BrandId getBrandId() {
        return brandId;
    }

    public void setBrandId(BrandId brandId) {
        this.brandId = brandId;
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

    public enum BrandId {
        Volkswagen, BMW, Opel, Mercedes, Honda, Toyota, Nissan, Subaru, Chevrolet, Ford, Dodge
    }
    private int id;
<<<<<<< Updated upstream
    private Brand brand;
    private String series;
=======

    private BrandId brandId;
    private String model;
>>>>>>> Stashed changes
    private int year;

}
