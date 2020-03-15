package com.myappcompany.thea.mobileappthryve;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectData {
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccessful = false;

    public List<Map<String,String>> getData() {
        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();

        try {
            JdbcPostgresqlConnection instance = new JdbcPostgresqlConnection();
            connect = instance.connections();        // Connect to database

            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                // Change below query according to your own database.
                String query = "SELECT * FROM public.\"Career-Staff\"";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Map<String, String> datanum = new HashMap<String, String>();
                    datanum.put("ID", rs.getString("CountryId"));
                    datanum.put("Country", rs.getString("CountryName"));
                    datanum.put("Capital", rs.getString("CapitalCity"));
                    data.add(datanum);
                }


                ConnectionResult = " successful";
                isSuccessful = true;
                connect.close();
            }
        } catch (Exception ex) {
            isSuccessful = false;
            ConnectionResult = ex.getMessage();
        }

        return data;
    }
}
