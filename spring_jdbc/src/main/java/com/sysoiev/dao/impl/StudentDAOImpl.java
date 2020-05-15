package com.sysoiev.dao.impl;

import com.sysoiev.dao.StudentDAO;
import com.sysoiev.dao.StudentMapper;
import com.sysoiev.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
//CREATE TABLE IF NOT EXISTS products (Id INT PRIMARY KEY AUTO_INCREMENT, ProductName VARCHAR(20), Price INT)
    @Override
    public void createTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS Student(ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT, NAME VARCHAR(20), AGE INT);";
        jdbcTemplate.execute(SQL);
        System.out.println("New table was created ");
    }

    @Override
    public void dropTable() {
        String SQL = "DROP TABLE Student;";
        jdbcTemplate.execute(SQL);
        System.out.println("Table was deleted");
    }

    @Override
    public void create() throws IOException {
        System.out.println("Enter name :");
        String nameStudent = reader.readLine();
        System.out.println("Enter age :");
        int ageStudent = Integer.parseInt(reader.readLine());
        String SQL = "insert into Student (name, age) values (?, ?)";
        jdbcTemplate.update(SQL, nameStudent, ageStudent);
        System.out.println("Created Record Name = " + nameStudent + " Age = " + ageStudent);
    }

    @Override
    public void getStudent() throws IOException {
        System.out.println("Enter id of the student which data you want to see :");
        int idStudent = Integer.parseInt(reader.readLine());
        String SQL = "select * from Student where id = ?";
        Student student = jdbcTemplate.queryForObject(SQL, new Object[]{idStudent}, new StudentMapper());
        System.out.println(student.toString());
    }

    @Override
    public void listStudents() {
        String SQL = "select * from Student";
        List<Student> students = jdbcTemplate.query(SQL, new StudentMapper());
        System.out.println("------Listing Multiple Records--------");
        for (Student record : students) {
            System.out.print("ID : " + record.getId());
            System.out.print(", Name : " + record.getName());
            System.out.println(", Age : " + record.getAge());
        }
    }

    @Override
    public void delete() throws IOException {
        System.out.println("Enter student's id which you want to delete :");
        int idStudent = Integer.parseInt(reader.readLine());
        String SQL = "delete from Student where id = ?";
        jdbcTemplate.update(SQL, idStudent);
        System.out.println("Deleted Record with ID = " + idStudent);
    }

    @Override
    public void updateAge() throws IOException {
        System.out.println("Enter new age :");
        int newAge = Integer.parseInt(reader.readLine());
        System.out.println("Enter id of the student which name you want to update :");
        int idStudent = Integer.parseInt(reader.readLine());
        String SQL = "update Student set age = ? where id = ?";
        jdbcTemplate.update(SQL, newAge, idStudent);
        System.out.println("Updated Record with ID = " + idStudent);
    }

    @Override
    public void updateName() throws IOException {
        System.out.println("Enter new name :");
        String nameStudent = reader.readLine();
        System.out.println("Enter id of the student which name you want to update :");
        int idStudent = Integer.parseInt(reader.readLine());
        String SQL = "update Student set name = ? where id = ?";
        jdbcTemplate.update(SQL, nameStudent, idStudent);
        System.out.println("Updated Record with ID = " + idStudent);
    }


}
