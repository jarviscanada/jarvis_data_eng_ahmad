package ca.jrvs.apps.twitter.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.IOException;

public class Student {
    private int id;
    private String name;
    private String school;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public static void main(String[] args) throws IOException {
        Student st = new Student();
        st.setId(1); st.setName("Ahmad"); st.setSchool("BDC");
        ObjectMapper mapper = new ObjectMapper();
        String json_student = mapper.writeValueAsString(st);
        System.out.println(json_student);

        String json_test = "{\"id\": 1 ,\"name\": \"Ali\" ,\"school\" : \"Holly\", \"address\" : \"Patanahee\" }";
        System.out.println(json_test);

         String companyStr = "{\n"
                + "   \"symbol\":\"AAPL\",\n"
                + "   \"companyName\":\"Apple Inc.\",\n"
                + "   \"exchange\":\"Nasdaq Global Select\",\n"
                + "   \"description\":\"Apple Inc is designs, manufactures and markets mobile communication and media devices and personal computers, and sells a variety of related software, services, accessories, networking solutions and third-party digital content and applications.\",\n"
                + "   \"CEO\":\"Timothy D. Cook\",\n"
                + "   \"sector\":\"Technology\",\n"
                + "   \"financials\":[\n"
                + "      {\n"
                + "         \"reportDate\":\"2018-12-31\",\n"
                + "         \"grossProfit\":32031000000,\n"
                + "         \"costOfRevenue\":52279000000,\n"
                + "         \"operatingRevenue\":84310000000,\n"
                + "         \"totalRevenue\":84310000000,\n"
                + "         \"operatingIncome\":23346000000,\n"
                + "         \"netIncome\":19965000000\n"
                + "      },\n"
                + "      {\n"
                + "         \"reportDate\":\"2018-09-30\",\n"
                + "         \"grossProfit\":24084000000,\n"
                + "         \"costOfRevenue\":38816000000,\n"
                + "         \"operatingRevenue\":62900000000,\n"
                + "         \"totalRevenue\":62900000000,\n"
                + "         \"operatingIncome\":16118000000,\n"
                + "         \"netIncome\":14125000000\n"
                + "      }\n"
                + "   ],\n"
                + "   \"dividends\":[\n"
                + "      {\n"
                + "         \"exDate\":\"2018-02-09\",\n"
                + "         \"paymentDate\":\"2018-02-15\",\n"
                + "         \"recordDate\":\"2018-02-12\",\n"
                + "         \"declaredDate\":\"2018-02-01\",\n"
                + "         \"amount\":0.63\n"
                + "      },\n"
                + "      {\n"
                + "         \"exDate\":\"2017-11-10\",\n"
                + "         \"paymentDate\":\"2017-11-16\",\n"
                + "         \"recordDate\":\"2017-11-13\",\n"
                + "         \"declaredDate\":\"2017-11-02\",\n"
                + "         \"amount\":0.63\n"
                + "      }\n"
                + "   ]\n"

                + "}";
System.out.println(companyStr);
    }
}
