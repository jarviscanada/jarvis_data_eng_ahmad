package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterRestDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.AdditionalAnswers;
import org.mockito.stubbing.Answer;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
    @Mock
    TwitterRestDao dao;

    @InjectMocks
    TwitterService service;

    public Tweet buildTweet(String text, double lat , double lon){
        Tweet tweet = new Tweet();
        Coordinates geo_coordinates = new Coordinates();
        geo_coordinates.setCoordinates(new double[]{lat, lon});
        tweet.setCoordinates(geo_coordinates);
        tweet.setText(text);
        return tweet;
    }

    public Tweet buildTweet(String id){
        Tweet tweet = new Tweet();
        tweet.setId_str(id);
        return tweet;
    }

    public void ExceptionPostTest() throws URISyntaxException, OAuthExpectationFailedException, IOException, JSONException, OAuthMessageSignerException, OAuthCommunicationException {
            Tweet actualTweetGeo = buildTweet("tweet",100d,1d);
            String tweet_text = "It's New Year's Eve and only a couple of hours until the next millennium.The reporter finds a man on the street.What are your plans for the next millennium? she asks him.";
            String expected_msg = "Arguments are out of bound...";
            Tweet actualTweetText = buildTweet(tweet_text,1d,-1d);
            try{
                service.postTweet(actualTweetGeo);
                fail();
            }catch(IllegalArgumentException e){
                assertEquals(expected_msg,e.getMessage());
            }
            try{
                service.postTweet(actualTweetText);
                fail();
            }catch(IllegalArgumentException e){
                assertEquals(expected_msg,e.getMessage());
            }

    }

    public void ExceptionShowTest() throws URISyntaxException, OAuthExpectationFailedException, IOException, JSONException, OAuthMessageSignerException, OAuthCommunicationException {
        String tweet_id = "abc123";
        String[] fields  = {"id","id_str"};
        String expected_msg = "Id should be a number only...";
        Tweet tweet = buildTweet(tweet_id);
        try{
            service.showTweet(tweet_id,fields);
        }catch(IllegalArgumentException e){
            assertEquals(expected_msg,e.getMessage());
        }
    }

    public void ExceptionDeleteTest() throws URISyntaxException, OAuthExpectationFailedException, IOException, JSONException, OAuthMessageSignerException, OAuthCommunicationException {
        String[] tweet_ids = {"123","abc"};
        String expected_msg = "Id should be a number only...";
        when(dao.deleteById(any(String.class))).thenReturn(buildTweet(tweet_ids[0])).thenReturn(buildTweet(tweet_ids[1]));
        try{
            service.deleteTweets(tweet_ids);
        }catch(IllegalArgumentException e){
            assertEquals(expected_msg,e.getMessage());
        }
    }

    @Test
    public void postTweettest() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, JSONException, URISyntaxException, OAuthCommunicationException {
        Tweet expectedTweet = buildTweet("tweet",1d,-1d);
        when(dao.create(any(Tweet.class))).thenReturn(expectedTweet);
        Tweet actualTweet = service.postTweet(expectedTweet);
        assertNotNull(actualTweet);
        assertEquals(expectedTweet.getText(),actualTweet.getText());
        assertEquals(expectedTweet.getCoordinates().getCoordinates(),actualTweet.getCoordinates().getCoordinates());
        ExceptionPostTest();

    }



    @Test
    public void showTweettest() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, JSONException, URISyntaxException, OAuthCommunicationException {
        String tweet_id = "1234";
        String[] fields = {"id","id_str"};
        Tweet expectedTweet = buildTweet(tweet_id);
        when(dao.findById(tweet_id)).thenReturn(expectedTweet);
        Tweet actualTweet = service.showTweet(tweet_id,fields);
        assertNotNull(actualTweet);
        assertEquals(expectedTweet.getId_str(),actualTweet.getId_str());
        ExceptionShowTest();

    }

    @Test
    public void deleteTweettest() throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, JSONException, URISyntaxException, OAuthCommunicationException {
        String[] tweet_ids = {"123","456","789"};
        List<Tweet> expectedTweets = new ArrayList<Tweet>();
        for(String id : tweet_ids){
            Tweet tweet = buildTweet(id);
            expectedTweets.add(tweet);

        }
        when(dao.deleteById(any(String.class))).thenReturn(buildTweet(tweet_ids[0])).thenReturn(buildTweet(tweet_ids[1])).thenReturn(buildTweet(tweet_ids[2]));
        List<Tweet> actualTweets = service.deleteTweets(tweet_ids);
        assertNotNull(actualTweets);
        assertEquals(expectedTweets.size(),actualTweets.size());
        assertEquals(expectedTweets.get(0).getId_str(),actualTweets.get(0).getId_str());
        ExceptionDeleteTest();

    }

}