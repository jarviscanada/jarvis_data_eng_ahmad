package ca.jrvs.apps.twitter.example;

public class UsingService {
    private Service service;
    public UsingService(Service service) {
        this.service=service;
    }

    public String ServiceCredentials(String user){
        String u = user + "is getting -> ";
        String str = this.service.getName() + this.service.getVendor() + AnotherService.getYear();
        System.out.println("str= "+str);
        return u+str;
    }

}
