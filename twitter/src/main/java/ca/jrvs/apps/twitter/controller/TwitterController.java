package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwitterController implements Controller {

    private String DELIMITER = ":";
    private String SEPERATOR = ",";
    private Tweet tweet = null;
    private Coordinates coordinates;
    private Service service;

    public TwitterController(Service service) {
        this.service = service;
    }

    @Override
    public Tweet postTweet(String[] args) throws URISyntaxException, OAuthExpectationFailedException, IOException, JSONException, OAuthMessageSignerException, OAuthCommunicationException {
        if(args.length !=3){
            try{
            throw new IllegalArgumentException("The number of arguments entered should be equal to 3....");}
            catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
        else {
            String[] geo_coordinates=null;
            String tweet_text = null;
            double lat = 0d;
            double lon = 0d;


            try{ geo_coordinates = args[2].split(DELIMITER);
                 tweet_text = args[1];
            }
            catch(Exception e){System.out.println("The delimiter entered between coordinates should be : ");}

            if(geo_coordinates.length!=2 || tweet_text.isEmpty()){
                try{
                    throw new IllegalArgumentException("The arguments entered are invalid...");}
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }
            else{
                try{
                    lat = Double.parseDouble(geo_coordinates[0]);
                    lon = Double.parseDouble(geo_coordinates[1]);
                }
                catch(Exception e){
                    System.out.println("The values of latitude and longitude should be numbers...");
                }
                coordinates.setCoordinates(new double[] {lat,lon});
                tweet.setText(tweet_text);
                tweet.setCoordinates(coordinates);
            }

        }

                return service.postTweet(tweet);
    }

    @Override
    public Tweet showTweet(String[] args) throws URISyntaxException, OAuthExpectationFailedException, IOException, JSONException, OAuthMessageSignerException, OAuthCommunicationException {
        List<String> features = new ArrayList<String>(Arrays.asList("created_at","id","id_str","text","entities","hashtags","user_mentions","retweet_count", "favorite_count", "favorited", "retweeted"));
        String[] options = null;
        if(args.length!=3){
            try{
                throw new IllegalArgumentException("The number of arguments entered should be equal to 3....");}
            catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

        }
        else{
            try{
                options = args[2].split(SEPERATOR);
            }
            catch(Exception e){
                System.out.println("The delimiter entered between options should be , ....");
            }
            for(String option : options){
                if(!features.contains(option)){
                    try{
                    throw new IllegalArgumentException("Options given should lie as a feature in Tweet object....");}
                    catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                }
            }
        }
       return service.showTweet(args[1],options);
    }


    @Override
    public List<Tweet> deleteTweet(String[] args) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, JSONException, URISyntaxException, OAuthCommunicationException {
       if(args.length!=2){
           try {
               throw new IllegalArgumentException("The number of arguments entered should be equal to two..");
           }catch(IllegalArgumentException e){
               System.out.println(e.getMessage());
           }
       }
       else{

           return service.deleteTweets(args[1].split(SEPERATOR));

       }
        return null;

    }

}
