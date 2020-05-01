package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import java.net.URI;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TwitterHttpHelper implements HttpHelper{

    private OAuthConsumer consumer;
    private CloseableHttpClient httpClient;

    public TwitterHttpHelper(String consumerKey, String consumerSecret , String accessToken, String tokenSecret){
        this.consumer = new CommonsHttpOAuthConsumer(consumerKey,consumerSecret);
        this.consumer.setTokenWithSecret(accessToken,tokenSecret);
        this.httpClient = HttpClientBuilder.create().build();
    }
    @Override
    public HttpResponse httpGet(URI uri) throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException, IOException {
        HttpGet get_request = new HttpGet(uri);
        this.consumer.sign(get_request);
        CloseableHttpResponse response =this.httpClient.execute(get_request);
        return response;
    }

    @Override
    public HttpResponse httpPost(URI uri) throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException, IOException {
        HttpPost post_request = new HttpPost(uri);
        this.consumer.sign(post_request);
        CloseableHttpResponse response =this.httpClient.execute(post_request);
        return response;
    }


}
