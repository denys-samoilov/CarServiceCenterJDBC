package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class Controller extends HttpServlet {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("CarPU");

    public void init(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        super.init();
        System.out.println("Метод ініціалізації спрацював");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int carId = Integer.parseInt(request.getParameter("id"));

        EntityManager em = emf.createEntityManager();
        Car car = em.find(Car.class, carId);
        em.close();
            System.out.println("\nID бренду: " + car.getBrand());
            System.out.println("\nМодель: " + car.getModel());
            System.out.println("\nРік: " + car.getYear());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int carId = Integer.parseInt(request.getParameter("id"));
        String model = request.getParameter("model");
        String brand = request.getParameter("brandid");
        int year = Integer.parseInt(request.getParameter("year"));


        Car.Brand brandName = Car.Brand.valueOf(brand);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();


        Car car = em.find(Car.class, carId);
        if (car != null) {
            car.setModel(model);
            car.setBrand(brandName);
            car.setYear(year);

            em.merge(car);
        }

        em.getTransaction().commit();
        em.close();

      System.out.println("Зміни внесено успішно");
    }
}
