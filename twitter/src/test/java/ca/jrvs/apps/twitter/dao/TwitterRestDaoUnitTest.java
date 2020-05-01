package ca.jrvs.apps.twitter.dao;



import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.databind.ObjectMapper;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import static org.junit.Assert.*;
@RunWith(PowerMockRunner.class)
@PrepareForTest({EntityUtils.class, URI.class})

public class TwitterRestDaoUnitTest {

    @Mock
    HttpHelper httphelper;



    @InjectMocks
    TwitterRestDao trd;


    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH= "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy/";
    //URI symbols
    private static final String QUERY_SYM="?";
    private static final String AMPERSAND="&";
    private static final String EQUAL= "=";



    ObjectMapper mapper = new ObjectMapper();

    public String test_tweet = "{\n" +
            "   \"created_at\":\"Mon April 20 21:24:39 +0000 2020\",\n" +
            "   \"id\":1097607853932564480,\n" +
            "   \"id_str\":\"1097607853932564480\",\n" +
            "   \"text\":\"This is a tweet used for testing\",\n" +
            "   \"entities\":{\n" +
            "      \"hashtags\":[],      \n" +
            "      \"user_mentions\":[]  \n" +
            "   },\n" +
            "   \"coordinates\":null,    \n" +
            "   \"retweet_count\":0,\n" +
            "   \"favorite_count\":0,\n" +
            "   \"favorited\":false,\n" +
            "   \"retweeted\":false\n" +
            "}";

    Tweet expected_tweet;

    {
        try {
            expected_tweet = mapper.readValue(test_tweet,Tweet.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before

    public void TestSetup() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {

        // Mocks to be used in setup
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        HttpEntity httpEntity = mock(HttpEntity.class);
        PowerMockito.mockStatic(EntityUtils.class);



        //Setup conditions
        when(httphelper.httpGet(any(URI.class))).thenReturn(response);
        when(httphelper.httpPost(any(URI.class))).thenReturn(response);
        when(response.getEntity()).thenReturn(httpEntity);
        when(EntityUtils.toString(any(HttpEntity.class))).thenReturn(test_tweet);

    }

    @Test
    public void createTest() throws JSONException, IOException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, URISyntaxException {
        double[] cord = {-1d,1d};
        String txt = "test";

        Tweet tweet = mock(Tweet.class);
        Coordinates coordinates = mock(Coordinates.class);

        when(tweet.getText()).thenReturn(txt);
        when(tweet.getCoordinates()).thenReturn(coordinates);
        when(coordinates.getCoordinates()).thenReturn(cord);

        Tweet tweet_create = trd.create(tweet);
        testCases(tweet_create);

    }



    @Test
    public void findByIdTest() throws JSONException, IOException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, URISyntaxException {

        Tweet tweet_find = trd.findById(expected_tweet.getId_str());
        testCases(tweet_find);

    }

    @Test
    public void deleteByIdTest() throws JSONException, IOException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, URISyntaxException {

        Tweet tweet_delete = trd.deleteById(expected_tweet.getId_str());
        testCases(tweet_delete);

    }



    public void testCases(Tweet actual_tweet){

        assertNotNull(actual_tweet);
        assertEquals(expected_tweet.getId_str(),actual_tweet.getId_str());
        assertEquals(expected_tweet.getText(),actual_tweet.getText());
    }


}