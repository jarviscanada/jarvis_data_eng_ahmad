package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {
    public static void main(String[] args) {
        DataConnectionManager dcm = new DataConnectionManager("localhost", "hplussport", "postgres", "password");
        try {

            Connection conn = dcm.getConnection();
            OrderDAO orderdao = new OrderDAO(conn);
            Order order = orderdao.findById(1001);
            System.out.println(order);







        }catch(SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        }
    }
}
