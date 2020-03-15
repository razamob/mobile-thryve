package com.myappcompany.thea.mobileappthryve;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcPostgresqlConnection {

    @SuppressLint("NewApi")
    public Connection connections() {
        Connection conn1 = null;

        try {
            String dbURL = "jdbc:postgresql://thryve-database-2020.clkzzm3gdrpr.us-east-2.rds.amazonaws.com:5432/thryve-database-2020";
            String user = "thryve2020";
            String pass = "Thryve2020";

            conn1 = DriverManager.getConnection(dbURL, user, pass);
            if (conn1 != null) {
                System.out.println("Connected to database #1");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                if (conn1 != null && !conn1.isClosed()) {
                    conn1.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return conn1;
    }
}
