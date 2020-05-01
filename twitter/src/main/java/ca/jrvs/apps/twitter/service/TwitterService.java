package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterRestDao;
import ca.jrvs.apps.twitter.model.Tweet;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;



public class TwitterService implements Service {
    private TwitterRestDao dao;
    private int MAX_COUNT = 140;
    private double LAT_MAX = 80d;
    private double LAT_MIN = -180d;
    private double LON_MAX = 90d;
    private double LON_MIN = -90d;

    public TwitterService(TwitterRestDao dao) {
        this.dao = dao;
    }

    @Override
    public Tweet postTweet(Tweet tweet) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, JSONException, URISyntaxException, OAuthCommunicationException {
        double lon = tweet.getCoordinates().getCoordinates()[0];
        double lat = tweet.getCoordinates().getCoordinates()[1];
        if( (tweet.getText().length() > MAX_COUNT) || (lat<=LAT_MIN || lat >= LAT_MAX)   || (lon<=LON_MIN || lon>=LON_MAX)  ){
            throw new IllegalArgumentException("Arguments are out of bound...");}

            return dao.create(tweet);

    }

    @Override
    public Tweet showTweet(String id, String[] fields) throws OAuthMessageSignerException, OAuthExpectationFailedException, IOException, JSONException, URISyntaxException, OAuthCommunicationException {
        Tweet show_tweet;
        if(!id.matches("[0-9]+")){
            throw new IllegalArgumentException("Id should be a number only...");
        }
            show_tweet = dao.findById(id);
            return show_tweet;
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) throws URISyntaxException, OAuthExpectationFailedException, IOException, JSONException, OAuthMessageSignerException, OAuthCommunicationException {
        List<Tweet> tweet_list = new ArrayList<Tweet>();
        for (String id : ids) {
            if (!id.matches("[0-9]+")) {
                throw new IllegalArgumentException("Id should be a number only...");
            }
            tweet_list.add(dao.deleteById(id));

        }
        return tweet_list;

    }



}
