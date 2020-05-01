package ca.jrvs.apps.twitter.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@RunWith(PowerMockRunner.class)
@PrepareForTest({AnotherService.class})

public class UsingServiceTest {


    @Test
    public void UsingServiceTestCase(){
        Service service = mock(Service.class);
        PowerMockito.mockStatic(AnotherService.class);
        UsingService us = new UsingService(service);
        when(service.getName()).thenReturn("Database ");
        when(service.getVendor()).thenReturn(" : Oracle");
        when(AnotherService.getYear()).thenReturn("1983");
        System.out.println(us.ServiceCredentials("Bob"));

    }
}