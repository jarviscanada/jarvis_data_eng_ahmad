package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.client.methods.CloseableHttpResponse;
import static java.nio.charset.StandardCharsets.UTF_8;
import org.junit.Test;

import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class TwitterHttpHelperTest {


    String CONSUMER_KEY = System.getenv("consumerKey");
    String CONSUMER_SECRET = System.getenv("consumerSecret");
    String ACCESS_TOKEN = System.getenv("accessToken");
    String TOKEN_SECRET = System.getenv("tokenSecret");

    TwitterHttpHelper twitter_handle = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);


    String url_get = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=AhmadNayyarHas1";
    URI uri_get;

    {
        try {
            uri_get = new URI(url_get);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    String url_content = URLEncoder.encode("Welcome to Test Cases in Jarvis", UTF_8.toString());
    String url_post = "https://api.twitter.com/1.1/statuses/update.json?status=" + url_content;
    URI uri_post = new URI(url_post);


    public TwitterHttpHelperTest() throws UnsupportedEncodingException, URISyntaxException {
    }

    @Test
    public void httpGet() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {

        CloseableHttpResponse response = (CloseableHttpResponse) twitter_handle.httpGet(uri_get);
        String out_get = Arrays.stream(response.getAllHeaders()).filter(i -> i.getName().toString().equals("status")).map(i -> i.getValue()).collect(Collectors.joining());
        assertEquals("200 OK", out_get);


    }

    @Test
    public void httpPost() throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException {

        CloseableHttpResponse response = (CloseableHttpResponse) twitter_handle.httpPost(uri_post);
        String out_post = Arrays.stream(response.getAllHeaders()).filter(i -> i.getName().toString().equals("status")).map(i -> i.getValue()).collect(Collectors.joining());
        assertEquals("200 OK", out_post);


    }
}