package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class TwitterRestDaoIntTest {
    private  String CONSUMER_KEY;
    private  String  CONSUMER_SECRET;
    private  String  ACCESS_TOKEN;
    private  String   TOKEN_SECRET;
    private  String request_type = "GET";
    private String test_statement="";
    private String expected_tweet_id = "1251421300431753217";
    private String expected_tweet_text = "Hi #Hello how are you myyy boyss #YouthWithYou @AhmadNayyarHas1";


    private HttpHelper httpHelper;
    private TwitterRestDao trd;
    private URI uri_test;



    @Before
    public void testSetup() {

       CONSUMER_KEY = System.getenv("consumerKey");
       CONSUMER_SECRET = System.getenv("consumerSecret");
       ACCESS_TOKEN = System.getenv("accessToken");
       TOKEN_SECRET = System.getenv("tokenSecret");


        httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        String test_statement = "https://api.twitter.com" + "/1.1/statuses/show.json" + "?id=1251421300431753217";

        {
            try {
                uri_test = new URI(test_statement);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        trd = new TwitterRestDao(httpHelper);

    }
    @Test
    public void parseResponsesTest() throws JSONException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {
        String actual_str  = trd.parseHttpResponse(uri_test,request_type);
        Tweet actual_tweet = trd.toTweet(actual_str);
        testCases(actual_tweet);

    }

    public void testCases(Tweet actual_tweet){
        assertNotNull(actual_tweet);
        assertNotNull(actual_tweet.getEntities());
        assertEquals(expected_tweet_id,actual_tweet.getId_str());
        assertEquals(expected_tweet_text,actual_tweet.getText());
    }

}