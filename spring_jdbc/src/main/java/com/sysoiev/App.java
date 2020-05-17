package com.sysoiev;

import com.sysoiev.dao.impl.StudentDAOImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        StudentDAOImpl studentJDBCTemplate = (StudentDAOImpl) context.getBean("studentJDBCTemplate");
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
                        studentJDBCTemplate.create();
                        break;
                    case 2:
                        studentJDBCTemplate.updateName();
                        break;
                    case 3:
                        studentJDBCTemplate.updateAge();
                        break;
                    case 4:
                        studentJDBCTemplate.delete();
                        break;
                    case 5:
                        studentJDBCTemplate.listStudents();
                        break;
                    case 6:
                        studentJDBCTemplate.getStudent();
                        break;
                    case 7:
                        studentJDBCTemplate.createTable();
                        break;
                    case 8:
                        studentJDBCTemplate.dropTable();
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

}
