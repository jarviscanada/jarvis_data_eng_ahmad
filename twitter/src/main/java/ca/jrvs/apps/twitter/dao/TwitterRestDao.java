package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.databind.ObjectMapper;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterRestDao implements CrdDao<Tweet,String> {


    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH= "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy/";
    //URI symbols
    private static final String QUERY_SYM="?";
    private static final String AMPERSAND="&";
    private static final String EQUAL= "=";

    //Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    public TwitterRestDao(HttpHelper httpHelper){this.httpHelper=httpHelper;}


    // Helper Functions

    public String format(String str){
        return "\"" + str + "\"" + ":";
    }
    public String format_val(String str){
        return "\"" + str + "\"";
    }

    public String parseHttpResponse(URI uri, String type) throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException, JSONException {
        //System.out.println("here0");
        CloseableHttpResponse response = null;
        if(type=="GET"){
        response = (CloseableHttpResponse) this.httpHelper.httpGet(uri);}
        else if(type=="POST"){
            response = (CloseableHttpResponse) this.httpHelper.httpPost(uri);
        }
        String output = EntityUtils.toString(response.getEntity());
        if(output.startsWith("[")){output = output.substring(1,output.length()-1);}
        return this.parseResponse(output);

    }

    public String parseResponse(String response) throws JSONException {
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
   public Tweet toTweet(String json_tweet) throws IOException {
       ObjectMapper mapper = new ObjectMapper();
       Tweet tweet = mapper.readValue(json_tweet,Tweet.class);
       return tweet;
   }
    @Override
    public Tweet create(Tweet entity) throws URISyntaxException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException, JSONException {

    String text = entity.getText();
    double lon = entity.getCoordinates().getCoordinates()[0];
    double lat = entity.getCoordinates().getCoordinates()[1];

    String post_query = API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + text + AMPERSAND +
            "long" + EQUAL + lon + AMPERSAND + "lat" + EQUAL + lat;
    URI uri_post = new URI(post_query);
    String json_tweet = this.parseHttpResponse(uri_post,"POST");
    Tweet tweet_find = this.toTweet(json_tweet);
    return tweet_find;



    }

    @Override
    public Tweet findById(String s) throws URISyntaxException, OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException, JSONException {
        String find_query = API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + s;
        URI uri_find = new URI(find_query);
        String json_tweet = this.parseHttpResponse(uri_find,"GET");
        Tweet tweet_find = this.toTweet(json_tweet);
        return tweet_find;
    }

    @Override
    public Tweet deleteById(String s) throws OAuthExpectationFailedException, OAuthCommunicationException, OAuthMessageSignerException, IOException, JSONException, URISyntaxException {
    String delete_query = API_BASE_URI + DELETE_PATH + s + ".json";
    URI uri_delete = new URI(delete_query);
    String json_tweet = this.parseHttpResponse(uri_delete,"GET");
    Tweet tweet_delete = this.toTweet(json_tweet);
    return tweet_delete;

    }



}
