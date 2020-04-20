package ca.jrvs.apps.jdbc;

import java.sql.*;
import java.util.List;

public class OrderDAO extends DataAccessObject<Order> {
    private String GET_ID_ORDER = "SELECT\n" +
            "  c.first_name, c.last_name, c.email, o.order_id,\n" +
            "  o.creation_date, o.total_due, o.status,\n" +
            "  s.first_name, s.last_name, s.email,\n" +
            "  ol.quantity,\n" +
            "  p.code, p.name, p.size, p.variety, p.price\n" +
            "from orders o\n" +
            "  join customer c on o.customer_id = c.customer_id\n" +
            "  join salesperson s on o.salesperson_id=s.salesperson_id\n" +
            "  join order_item ol on ol.order_id=o.order_id\n" +
            "  join product p on ol.product_id = p.product_id\n" +
            "where o.order_id = ?;";
    public OrderDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Order findById(long id) {
        Order order = new Order();

        try(PreparedStatement ps = connection.prepareStatement(GET_ID_ORDER); )
        {
            ps.setLong(1,id);
            ResultSet rs = ps.executeQuery();
            int counter = 0;
            while(rs.next()){
                counter++;
                if(counter==1){
                order.setId(id);
                order.setCustomer_first_name(rs.getString(1));
                order.setCustomer_last_name(rs.getString(2));
                order.setCustomer_email(rs.getString(3));
                order.setOrder_id(rs.getInt(4));
                order.setCreation_date(rs.getString(5));
                order.setOrder_total_due(rs.getDouble(6));
                order.setOrder_status(rs.getString(7));
                order.setSales_person_first_name(rs.getString(8));
                order.setSales_person_last_name(rs.getString(9));
                order.setSales_person_email(rs.getString(10));
                }
                OrderLine ord_line = new OrderLine();

                ord_line.setOrder_item_quantity(rs.getInt(11));
                ord_line.setProduct_code(rs.getString(12));
                ord_line.setProduct_name(rs.getString(13));
                ord_line.setProduct_size(rs.getInt(14));
                ord_line.setProduct_variety(rs.getString(15));
                ord_line.setProduct_price(rs.getDouble(16));

                order.getOrderLine_obj().add(ord_line);
            }

            return order;
        }catch(SQLException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
        }



    }

    @Override
    public Order create(Order dto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    protected int getLastVal(String sequence) {
        return super.getLastVal(sequence);
    }

    @Override
    public Order update(Order dto) {
        return null;
    }
}
