package com.sysoiev.dao;

import com.sysoiev.model.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class ManageEmployee {
    private static SessionFactory factory;

    public void run() throws IOException {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();

        factory = metadata.getSessionFactoryBuilder().build();
        ManageEmployee manageEmployee = new ManageEmployee();
        boolean isGo = true;
        try {
            while (isGo) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("\nChoose option, please :");
                System.out.println("Enter number : ");
                System.out.println("1. Insert rows ");
                System.out.println("2. Update row by name");
                System.out.println("3. Update row by age");
                System.out.println("4. Delete row ");
                System.out.println("5. Show all rows ");
                System.out.println("6. Get student by id ");
                System.out.println("7. Create new table ");
                System.out.println("8. Drop table ");
                System.out.println("9. End ");
                int number = Integer.parseInt(reader.readLine());
                switch (number) {
                    case 1:
                       // manageEmployee.addEmployee();
                        break;
                    case 2:
                        //studentJDBCTemplate.updateName();
                        break;
                    case 3:
                        // studentJDBCTemplate.updateAge();
                        break;
                    case 4:
                        //studentJDBCTemplate.delete();
                        break;
                    case 5:
                        //studentJDBCTemplate.listStudents();
                        break;
                    case 6:
                        //studentJDBCTemplate.getStudent();
                        break;
                    case 7:
                        //studentJDBCTemplate.createTable();
                        break;
                    case 8:
                        //studentJDBCTemplate.dropTable();
                        break;
                    case 9:
                        isGo = false;
                        break;
                    default:
                        System.out.println("Wrong number");
                        System.out.println("Enter number from 1 to 9, please");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method to CREATE an employee in the database
     *
     * @return
     */
    public Integer addEmployee(int id, String firstName, String lastName) {
        Session session = factory.openSession();
        Transaction transaction = null;
        Integer employeeId = null;
        try {
            transaction = session.beginTransaction();
            Employee employee = new Employee(id, firstName, lastName);
            employeeId = (Integer) session.save(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeId;
    }

    /**
     * Method to  READ all the employees
     */
    public void listEmployees() {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext(); ) {
                Employee employee = (Employee) iterator.next();
                System.out.print("Id: " + employee.getId());
                System.out.print("First Name: " + employee.getFirstName());
                System.out.println("Last Name: " + employee.getLastName());
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Method to UPDATE first name for an employee
     */
    public void updateEmployee(Integer EmployeeID, String firstName) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
            employee.setFirstName(firstName);
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Method to DELETE an employee from the records
     */
    public void deleteEmployee(Integer EmployeeID) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
