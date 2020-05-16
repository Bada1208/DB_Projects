package com.sysoiev;

import com.sysoiev.model.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Iterator;
import java.util.List;

public class App {
    private static SessionFactory factory;

    ///*try {
//         factory = new Configuration().configure().buildSessionFactory();
//      } catch (Throwable ex) {
//         System.err.println("Failed to create sessionFactory object." + ex);
//         throw new ExceptionInInitializerError(ex);
//      }
//
//      ManageEmployee ME = new ManageEmployee();
//
//      /* Add few employee records in database */
//Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
//    Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
//    Integer empID3 = ME.addEmployee("John", "Paul", 10000);
//
//    /* List down all the employees */
//      ME.listEmployees();
//
//    /* Update employee's records */
//      ME.updateEmployee(empID1, 5000);
//
//    /* Delete an employee from the database */
//      ME.deleteEmployee(empID2);
//
//    /* List down new list of the employees */
//      ME.listEmployees();*/
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();

        factory = metadata.getSessionFactoryBuilder().build();
       /*  Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Andrew");
        employee.setLastName("Kriak");

        session.save(employee);
        transaction.commit();
        System.out.println("Successfully saved");
        factory.close();
        session.close();*/
        App app = new App();

        Integer employee1 = app.addEmployee(2, "Vasia", "Ivanov");
        Integer employee2 = app.addEmployee(3, "Valera", "Petrow");
        Integer employee3 = app.addEmployee(4, "Hong", "Petrow");

        app.listEmployees();

        app.updateEmployee(employee3, "Ivan");

        app.deleteEmployee(employee2);

        app.listEmployees();

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
