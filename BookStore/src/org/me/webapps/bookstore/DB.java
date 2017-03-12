/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.webapps.bookstore;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
/**
 * Utilities class for getting DB connection
 * @author yklam2
 */
public class DB {
    private DB() {}
    
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String JDBC_URL = "jdbc:derby://ec2-54-212-206-37.us-west-2.compute.amazonaws.com:1527/BookStore";
    private static final String JDBC_USER = "book";
    private static final String JDBC_PASS = "book";
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
	return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }
    
}
