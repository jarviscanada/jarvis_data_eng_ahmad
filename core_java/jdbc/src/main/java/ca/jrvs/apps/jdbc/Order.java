package ca.jrvs.apps.jdbc;
//import ca.jrvs.apps.jdbc.OrderLine;

import java.util.ArrayList;
import java.util.List;

public class Order implements DataTransferObject{
    private long id;
    private String customer_first_name;
    private String customer_last_name;
    private String customer_email;
    private int order_id;
    private String creation_date;
    private double order_total_due;
    private String order_status;
    private String sales_person_first_name;
    private String sales_person_last_name;
    private String sales_person_email;
    private List<OrderLine> OrderLine_obj;


public Order(){
    OrderLine_obj = new ArrayList<OrderLine>();
}
public String toString(){
    String order_line_part = "orderLines : [{" + "\n";
    String order_part =  "id : " + getId() + "\n" +
            "customerFirstName: " + getCustomer_first_name() + "\n" +
            "customerLastName : " + getCustomer_last_name() + "\n" +
            "customerEmail : " + getCustomer_email() + "\n" +
            "creationDate : " + getCreation_date() + "\n" +
            "totalDue : " + getOrder_total_due() + "\n" +
            "status : " + getOrder_status() + "\n" +
            "salesPersonFirstName : " + getSales_person_first_name() + "\n" +
            "salesPersonLastName : " + getSales_person_last_name() + "\n" +
            "salesPersonEmail : " + getSales_person_email() + "\n";
    for(int i=0;i<OrderLine_obj.size();i++){
        order_line_part = order_line_part + OrderLine_obj.get(i).toString() + "\n";
        if(i<OrderLine_obj.size()-1) {
            order_line_part = order_line_part + "}, {" + "\n";
        }
        else{
            order_line_part = order_line_part + "} ]" + "\n";
        }
    }
return order_part + order_line_part;

}
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomer_first_name() {
        return customer_first_name;
    }

    public void setCustomer_first_name(String customer_first_name) {
        this.customer_first_name = customer_first_name;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_last_name() {
        return customer_last_name;
    }

    public void setCustomer_last_name(String customer_last_name) {
        this.customer_last_name = customer_last_name;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }


    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public double getOrder_total_due() {
        return order_total_due;
    }

    public void setOrder_total_due(double order_total_due) {
        this.order_total_due = order_total_due;
    }

    public String getSales_person_email() {
        return sales_person_email;
    }

    public void setSales_person_email(String sales_person_email) {
        this.sales_person_email = sales_person_email;
    }

    public String getSales_person_first_name() {
        return sales_person_first_name;
    }

    public void setSales_person_first_name(String sales_person_first_name) {
        this.sales_person_first_name = sales_person_first_name;
    }

    public String getSales_person_last_name() {
        return sales_person_last_name;
    }

    public void setSales_person_last_name(String sales_person_last_name) {
        this.sales_person_last_name = sales_person_last_name;
    }

    public List<OrderLine> getOrderLine_obj() {
        return OrderLine_obj;
    }
}

