package ca.jrvs.apps.twitter.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

public class TwitterApiTest {



    private static String CONSUMER_KEY = System.getenv("consumerKey");
    private static String CONSUMER_SECRET = System.getenv("consumerSecret");
    private static String ACCESS_TOKEN = System.getenv("accessToken");
    public static String TOKEN_SECRET = System.getenv("tokenSecret");

    public static String format(String str){
        return "\"" + str + "\"" + ":";
    }
    public static String format_val(String str){
        return "\"" + str + "\"";
    }

    public static String parseResponse(String response) throws JSONException {
        JSONObject jo = new JSONObject(response);

        String[] tweet_feats = {"created_at","id","id_str","text","entities","coordinates","retweet_count","favorite_count","favorited","retweeted"};
        String[] embedded_feats = {"hashtags","user_mentions"};
        String json_tweet = "";
        for(String feature : tweet_feats){
            if(feature.equals("entities")){
                json_tweet += " , " + format(feature) + "{ " + format(embedded_feats[0]) +
                        jo.getJSONObject(feature).getJSONArray(embedded_feats[0]) + " , " +
                        format(embedded_feats[1]) + jo.getJSONObject(feature).getJSONArray(embedded_feats[1]) + "}";
            }
            else if(feature.equals("created_at")){
                json_tweet += format(feature) + format_val(jo.getString(feature));
            }
            else if(feature.equals("text")){
                json_tweet += " , " + format(feature) + format_val(jo.getString(feature));
            }
            else if(feature.equals("id_str")){
                json_tweet += " , " +  format(feature) + format_val(jo.getString(feature));
            }
            else{
                json_tweet += " , " + format(feature) + jo.get(feature);
            }
        }
        json_tweet =  "{"+json_tweet+"}";

        return json_tweet;

    }

    public static String parseJson(String response) throws JSONException {


        String[] pairs = response.split(",");
        String[] tweet_feats = {"created_at","id","id_str","text","entities","coordinates","retweet_count","favorite_count","favorited","retweeted"};
        List<String> response_str;
        String json_tweet = "";
        int counter = 0;
        for(String feature : tweet_feats){
            counter++;
            response_str = Stream.of(pairs).filter(i->i.contains(feature)).map(i->i.replaceAll("\\{","").replaceAll("}","")).collect(Collectors.toList());
            if(response_str.size()>0 && counter > 1) {
                json_tweet += " , " + response_str.get(0) ;
            }
            else{
                json_tweet += response_str.get(0);
            }
        }


        return json_tweet;
    }





    public static void main(String[] args) throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException, InterruptedException, JSONException {
        //setup outh
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN,TOKEN_SECRET);

        //create an HTTP POST request
        //String content = "I am posting tweet through java my boys finally atlast";
        //String url_content = URLEncoder.encode(content, UTF_8.toString());
        //System.out.println("url_content = "+ url_content);
        String url_query = "https://api.twitter.com/1.1/statuses/update.json?status=";
        //HttpPost request = new HttpPost(url_query+url_content);
        HttpGet get_request = new HttpGet("https://api.twitter.com/1.1/statuses/show.json?id=1251421300431753217");
        consumer.sign(get_request);



        System.out.println("HTTP Request Headers:");
        Arrays.stream(get_request.getAllHeaders()).forEach(i->System.out.println(i));
        CloseableHttpClient client = HttpClientBuilder.create().build();

        CloseableHttpResponse response =(CloseableHttpResponse) client.execute(get_request);
        String output = EntityUtils.toString(response.getEntity());
        JSONObject jo = new JSONObject(output);
        System.out.println("technique="+jo.getJSONObject("entities").getJSONArray("hashtags") + jo.getJSONObject("entities").getJSONArray("user_mentions") );

        System.out.println("output = "+output);
        //System.out.println("json_tweet = " + parseJson(output));



System.out.println("json_tweet = "+ parseResponse(output));
String j_tweet = parseResponse(output);
ObjectMapper mapper = new ObjectMapper();


        }


    }

