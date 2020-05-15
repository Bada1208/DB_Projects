package com.sysoiev.dao;

import javax.sql.DataSource;
import java.io.IOException;

public interface StudentDAO {
    /**
     * This is the method to be used to initialize
     * database resources ie. connection.
     */
    void setDataSource(DataSource ds);

    void dropTable();

    void createTable();

    /**
     * This is the method to be used to create
     * a record in the Student table.
     */
    void create() throws IOException;

    /**
     * This is the method to be used to list down
     * a record from the Student table corresponding
     * to a passed student id.
     */
    void getStudent() throws IOException;

    /**
     * This is the method to be used to list down
     * all the records from the Student table.
     */
    void listStudents();

    /**
     * This is the method to be used to delete
     * a record from the Student table corresponding
     * to a passed student id.
     */
    void delete() throws IOException;

    /**
     * There are methods to be used to update
     * a record into the Student table.
     */
    void updateAge() throws IOException;

    void updateName() throws IOException;

}
