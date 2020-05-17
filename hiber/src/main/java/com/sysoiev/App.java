package com.sysoiev;

import com.sysoiev.dao.ManageEmployee;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        ManageEmployee manageEmployee = new ManageEmployee();
        manageEmployee.run();
         /*public static void main(String[] args) {


        App app = new App();

        Integer employee1 = app.addEmployee(2, "Vasia", "Ivanov");
        Integer employee2 = app.addEmployee(3, "Valera", "Petrow");
        Integer employee3 = app.addEmployee(4, "Hong", "Petrow");

        app.listEmployees();

        app.updateEmployee(employee3, "Ivan");

        app.deleteEmployee(employee2);

        app.listEmployees();

    }*/
    }
}