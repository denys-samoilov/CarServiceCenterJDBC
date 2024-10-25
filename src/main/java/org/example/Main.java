package org.example;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String jdbc_url = "jdbc:postgresql://localhost:5432/CarServiceDb";
//        try (var conn = DriverManager.getConnection(jdbc_url, "postgres", "1234")) {
//            var st = conn.createStatement();
//            var resultSet = st.executeQuery("select * from cars");
//            while (resultSet.next()) {
//                var id = resultSet.getString(1);
//                var name = resultSet.getString(2);
//                var model = resultSet.getString(3);
//                var year = resultSet.getString(4);
//                System.out.println("Id: " + id + "\nName: " + name + "\nModel: " + model + "\nYear: " + year);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        Scanner scanner = new Scanner(System.in);

        int loggedID = -1;


        ArrayList<User> userArrayList = new ArrayList<User>();
        ArrayList <Customer> customerArrayList = new ArrayList <Customer>();
        ArrayList <Order> orderArrayList = new ArrayList<Order>();
        ArrayList<Mechanic> mechanicArrayList = new ArrayList<Mechanic>();
        ArrayList<Car> carArrayList = new ArrayList<Car>();

        Mechanic mechanic = new Mechanic(0,"Сергій", "mechanic123", "qwerty123", "+380952278423");
        mechanicArrayList.add(mechanic);
        userArrayList.add(mechanic);

        while(true)
        {
            int k;
            System.out.println("1 - зареєструватись \n2 - увійти \n3 - зробити замовлення \n0 - вийти");

            k = scanner.nextInt();
            switch (k)
            {
                case 1: {
                    RegisterUser(userArrayList, customerArrayList, carArrayList,  jdbc_url); break;
                }

                case 2: {
                    loggedID = LoginUser(customerArrayList);
                    break;
                }

                case 3: {
                    Customer currentCustomer = null;

                    if(loggedID!=-1)
                    {
                        for(int i = 0; i<customerArrayList.size();i++)
                        {
                            if(customerArrayList.get(i).getId() == loggedID)
                                currentCustomer = customerArrayList.get(i);
                        }
                    }

                    if(currentCustomer == null)
                    {
                        System.out.println("Увійдіть в обліковий запис.");
                    }
                    else MakeOrder(orderArrayList, currentCustomer, mechanicArrayList, jdbc_url);

                    break;
                }

                case 0: {System.out.println("Виходимо..."); return;}

                default: {System.out.println("Введіть коректне значення");}
            }
        }
    }

    public static void RegisterUser(ArrayList<User> userArrayList, ArrayList<Customer> customerArrayList, ArrayList<Car> carArrayList, String jdbc_url)
    {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Введіть ім'я: ");
        String name = scanner.nextLine();

        System.out.print("Введіть логін: ");
        String login = scanner.nextLine();

        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();

        System.out.print("Введіть номер телефону: +38");
        String phone = "+38" + scanner.nextLine();


        System.out.println("Введіть індекс бренду свого автомобіля: ");
        for(int i = 0; i<Car.Brand.values().length;i++)
        {
            System.out.println(i+1 + " " + Car.Brand.values()[i]);
        }
        int carBrandId = scanner.nextInt()-1;


        scanner.nextLine();
        System.out.print("Введіть серію автомобіля: ");
        String carSeries = scanner.nextLine();

        System.out.print("Введіть рік автомобіля: ");
        int carYear = scanner.nextInt();

        String brandName = Car.Brand.values()[carBrandId].toString();


        try (var conn = DriverManager.getConnection(jdbc_url, "postgres", "1234")) {
            conn.setAutoCommit(false);

            var st = conn.createStatement();
            var resultSet = st.executeQuery("select * from customers");
            while (resultSet.next())
            {
                var dbLogin = resultSet.getString(3);
                if (login.equals(dbLogin)){
                    System.out.println("Даний логін вже існує");
                    return;
                }
            }
            PreparedStatement carInsert = conn.prepareStatement("insert into cars (model, year, brandId) VALUES (?, ?, ?)");
            carInsert.setString(1, carSeries);
            carInsert.setInt(2, carYear);
            carInsert.setInt(3, carBrandId);

            carInsert.executeUpdate();

            st = conn.createStatement();
            resultSet = st.executeQuery("select * from cars where id = (select max(id) from cars)");
            resultSet.next();
                int carId = resultSet.getInt(1);

            PreparedStatement userInsert = conn.prepareStatement("insert into customers (name, login, password, phoneNumber, carid) VALUES (?, ?, ?, ?, ?)");
            userInsert.setString(1, name);
            userInsert.setString(2, login);
            userInsert.setString(3, password);
            userInsert.setString(4, phone);
            userInsert.setInt(5, carId);
            userInsert.executeUpdate();

            st = conn.createStatement();
            resultSet = st.executeQuery("select * from customers where id = (select max(id) from customers)");
            resultSet.next();
            int customerId = resultSet.getInt(1);

            Car car = new Car(carId, carSeries, carYear, Car.Brand.values()[carBrandId]);

            Customer customer = new Customer(customerId, name, login, password, phone, car);
            System.out.println("Id користувача: " + customerId);
            userArrayList.add(customer);
            customerArrayList.add(customer);

            System.out.print("Вітаємо, " + name + "! \nВаш автомобіль:" + car.getBrand() + " " + car.getSeries() + " " + car.getYear() + "\nId машини: " + carId + "\n\n");

            conn.commit();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }

    public static void MakeOrder(ArrayList<Order> orderArrayList, Customer customer, ArrayList<Mechanic> mechanicArrayList, String jdbc_url)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть опис замовлення: ");
        String description = scanner.nextLine();

        System.out.println("Введіть індекс механіка до якого ви хочете звернутись: ");

        for(int i = 0; i< mechanicArrayList.size(); i++)
        {
            System.out.println(i+1 + " -> " + mechanicArrayList.get(i).getName());
        }

        var mechanicIndex = scanner.nextInt();

        try (var conn = DriverManager.getConnection(jdbc_url, "postgres", "1234")) {
            conn.setAutoCommit(false);
            var st = conn.createStatement();
            st = conn.createStatement();
            var resultSet = st.executeQuery("select * from customers");
            resultSet.next();
            var customerId = resultSet.getString(1);

            PreparedStatement orderInsert = conn.prepareStatement("insert into orders (customerId, mechanicId, description) VALUES (?, ?, ?)");
            orderInsert.setString(1, customerId);
            orderInsert.setInt(2, mechanicIndex);
            orderInsert.setString(3, description);

            orderInsert.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Order order = new Order(customerId, customer, mechanicArrayList.get(mechanicIndex), description);

        //orderArrayList.add(order);

        //OrderOutput(id, orderArrayList);
    }

    public  static void OrderOutput(int id, ArrayList<Order> orderArrayList)
    {
        System.out.println(orderArrayList.get(id));
    }

    public static int LoginUser(ArrayList<Customer> userArrayList)
    {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Логін: ");
        String inputLogin = scanner.nextLine();

        System.out.print("Пароль: ");
        String inputPassword = scanner.nextLine();

        int arrayListId;
        String arrayListLogin;
        String arrayListPassword;
        for(int i = 0; i<userArrayList.size(); i++)
        {
            arrayListId = userArrayList.get(i).getId();
            arrayListLogin = userArrayList.get(i).getLogin();
            arrayListPassword = userArrayList.get(i).getPassword();
            if(arrayListLogin.equals(inputLogin) && arrayListPassword.equals(inputPassword))
            {
                System.out.println("Вітаємо, " + userArrayList.get(i).getName() + "! Ваш ID: " + arrayListId);
                return arrayListId;
            }
        }

        System.out.println("Дані введено невірно, або користувача не зареєстровано");
        return -1;
    }

    public static ArrayList<Car> getCarsFromDB(String jdbc_url)
    {
        try (var conn = DriverManager.getConnection(jdbc_url, "postgres", "1234")) {
            ArrayList<Car> carArrayList = new ArrayList<>();

            var st = conn.createStatement();
            var resultSet = st.executeQuery("select * from cars");
            while (resultSet.next()) {
                var id = resultSet.getInt(1);
                var model = resultSet.getString(2);
                var year = resultSet.getInt(3);
                var brandId = resultSet.getInt(4);
                Car car = new Car(id, model, year, Car.Brand.values()[brandId]);
                carArrayList.add(car);
            }

            return  carArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public static ArrayList<User> getUsersFromDB(String jdbc_url)
//    {
//        try (var conn = DriverManager.getConnection(jdbc_url, "postgres", "1234")) {
//            conn.setAutoCommit(false);
//            ArrayList<User> userArrayList = new ArrayList<>();
//            var st = conn.createStatement();
//            var resultSet = st.executeQuery("select * from customers");
//            while (resultSet.next()) {
//                var id = resultSet.getInt(1);
//                var name = resultSet.getString(2);
//                var login = resultSet.getString(3);
//                var password = resultSet.getString(4);
//                var phoneNumber = resultSet.getString(5);
//                var carId = resultSet.getInt(6);
//
//                //Car car
//
//                Customer customer = new Customer(id, name,login,password,phoneNumber, carId);
//            }
//
//            return userArrayList;
//    }
//        catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
    }