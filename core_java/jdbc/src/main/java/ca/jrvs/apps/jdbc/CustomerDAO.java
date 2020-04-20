package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAO extends DataAccessObject<Customer> {

    private final String INSERT = "INSERT INTO customer (first_name, last_name," +
            "email, phone, address, city, state, zipcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String GET_ID = "SELECT first_name,last_name,email,phone,address,city,state,zipcode FROM " +
            "customer WHERE customer_id=?";
    private final String UPDATE = "UPDATE customer SET first_name=?,last_name=?,email=?,phone=?,"+
            "address=?,city=?,state=?,zipcode=? WHERE customer_id=?";
    private final String DELETE = "DELETE FROM customer where customer_id=?";

    public CustomerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Customer findById(long id) {
        Customer customer = new Customer();
        try(PreparedStatement ps = connection.prepareStatement(GET_ID);){
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                customer.setId(id);
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setZipCode(rs.getString("zipcode"));
            }
            return customer;

        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer update(Customer dto) {
        Customer customer = new Customer();
        try(PreparedStatement ps = this.connection.prepareStatement(UPDATE)){
            ps.setString(1,dto.getFirstName());
            ps.setString(2,dto.getLastName());
            ps.setString(3,dto.getEmail());
            ps.setString(4,dto.getPhone());
            ps.setString(5,dto.getAddress());
            ps.setString(6,dto.getCity());
            ps.setString(7,dto.getState());
            ps.setString(8,dto.getZipCode());
            ps.setLong(9,dto.getId());
            ps.execute();

            customer = this.findById(dto.getId());
            return customer;

        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public Customer create(Customer dto) {
        try(PreparedStatement ps = this.connection.prepareStatement(INSERT);){
            ps.setString(1,dto.getFirstName());
            ps.setString(2,dto.getLastName());
            ps.setString(3,dto.getEmail());
            ps.setString(4,dto.getPhone());
            ps.setString(5,dto.getAddress());
            ps.setString(6,dto.getCity());
            ps.setString(7,dto.getState());
            ps.setString(8,dto.getZipCode());
            ps.execute();
            int id = this.getLastVal(CUSTOMER_SEQUENCE);
            return this.findById(id);

        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement ps = this.connection.prepareStatement(DELETE)){
            ps.setLong(1,id);
            ps.execute();

        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
