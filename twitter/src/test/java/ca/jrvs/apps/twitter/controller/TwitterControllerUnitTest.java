package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {
    @Mock
    TwitterService service;

    @InjectMocks
    TwitterController controller;

    @Test
    public void postTweettest() {

    }

}